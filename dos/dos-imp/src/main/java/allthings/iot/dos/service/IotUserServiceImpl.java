package allthings.iot.dos.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.IotMessageCenterIdConfig;
import allthings.iot.dos.api.IotMessageService;
import allthings.iot.dos.api.IotUserService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.consumer.IotLoggerProducer;
import allthings.iot.dos.dao.IotUserDao;
import allthings.iot.dos.dao.IotUserProjectDao;
import allthings.iot.dos.dto.IotAuthorityDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import allthings.iot.dos.model.IotUser;
import allthings.iot.dos.model.IotUserProject;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.UUID;

/**
 * @author :  luhao
 * @FileName :  IotUserBizImpl
 * @CreateDate :  2018-5-25
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service(value = "iotUserService")
public class IotUserServiceImpl implements IotUserService {
    @Autowired
    private IotUserDao iotUserDao;

    @Autowired
    private IotUserProjectDao iotUserProjectDao;

    @Autowired
    private IotMessageService messageService;

    @Autowired
    private IotMessageCenterIdConfig config;

    @Autowired
    private IotLoggerProducer producer;

    @Override
    public ResultDTO<IotUserDTO> getUserByUsername(String username) {
        IotUser iotUser = iotUserDao.getIotUserByUsername(username);
        IotUserDTO iotUserDTO = null;
        if (iotUser != null) {
            iotUserDTO = new IotUserDTO();
            BeanUtils.copyProperties(iotUser, iotUserDTO);
            if (StringUtils.isNotBlank(iotUser.getAuthority())) {
                iotUserDTO.setAuthority(JSON.parseArray(iotUser.getAuthority(), IotAuthorityDTO.class));
            }
            iotUserDTO.setIotProjects(iotUserProjectDao.getProjectListByIotUserId(iotUser.getIotUserId()));
        }
        return ResultDTO.newSuccess(iotUserDTO);
    }

    /**
     * 添加用户
     *
     * @param iotUserDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> saveUser(IotUserDTO iotUserDTO) {
        if (!iotUserDTO.getPassword().equals(iotUserDTO.getPassword2())) {
            return ResultDTO.newFail(ErrorCode.ERROR_8017.getCode(),
                    ErrorCode.ERROR_8017.getMessage());
        }

        IotMessageManagerDTO iotMessageManagerDTO = new IotMessageManagerDTO();
        iotMessageManagerDTO.setCode(iotUserDTO.getCode());
        iotMessageManagerDTO.setMobileNumber(iotUserDTO.getMobile());
        ResultDTO<Integer> result = messageService.validateIdentifyCode(iotMessageManagerDTO);
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }

        IotUser iotUser = iotUserDao.getIotUserByMobile(iotUserDTO.getMobile());
        if (iotUser != null) {
            return ResultDTO.newFail(ErrorCode.ERROR_8000.getCode(),
                    ErrorCode.ERROR_8000.getMessage());
        }
        iotUser = iotUserDao.getIotUserByUsername(iotUserDTO.getUsername());
        if (iotUser != null) {
            return ResultDTO.newFail(ErrorCode.ERROR_8001.getCode(),
                    ErrorCode.ERROR_8001.getMessage());
        }
        IotUser user = getIotUser(iotUserDTO);
        iotUserDao.save(user);
        saveProjectLogger(user, Constants.CREATE_OPERATE, null);
        return ResultDTO.newSuccess(user.getIotUserId());
    }

    /**
     * 组装 IotUser
     *
     * @param iotUserDTO
     * @return
     */
    private IotUser getIotUser(IotUserDTO iotUserDTO) {
        IotUser iotUser = new IotUser();
        BeanUtils.copyProperties(iotUserDTO, iotUser);
        iotUser.setRoleCode(RoleCode.USER);
        iotUser.setEnabled(true);
        iotUser.setCreateOperatorId(RoleCode.CREATE_OPERATOR_ID);
        iotUser.setModifyOperatorId(RoleCode.CREATE_OPERATOR_ID);
        iotUser.setAuthority(Strings.nullToEmpty(iotUser.getAuthority()));
        iotUser.setEmail(Strings.nullToEmpty(iotUser.getEmail()));
        String salt = UUID.randomUUID().toString();
        String password = DigestUtils.md5DigestAsHex(iotUserDTO.getPassword().getBytes());
        password = DigestUtils.md5DigestAsHex(password.concat(salt).getBytes());
        iotUser.setPassword(password);
        iotUser.setSalt(salt);
        return iotUser;
    }

    /**
     * 编辑用户
     *
     * @param iotUserDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateUser(IotUserDTO iotUserDTO) {
        Long iotUserId = iotUserDTO.getModifyOperatorId();
        if (iotUserId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_8018.getCode(),
                    ErrorCode.ERROR_8018.getMessage());
        }
        IotMessageManagerDTO iotMessageManagerDTO = new IotMessageManagerDTO();
        iotMessageManagerDTO.setCode(iotUserDTO.getCode());
        iotMessageManagerDTO.setMobileNumber(iotUserDTO.getMobile());
        ResultDTO<Integer> result = messageService.validateIdentifyCode(iotMessageManagerDTO);
        if (!result.isSuccess()) {
            return result;
        }
        IotUser iotUser = iotUserDao.getIotUserByMobile(iotUserDTO.getMobile());
        if (iotUser != null && !iotUserId.equals(iotUser.getIotUserId())) {
            return ResultDTO.newFail(ErrorCode.ERROR_8000.getCode(),
                    ErrorCode.ERROR_8000.getMessage());
        }

        String email = Strings.nullToEmpty(iotUserDTO.getEmail());
        iotUserDao.updateUser(iotUserDTO.getModifyOperatorId(), iotUserDTO.getRealName(), iotUserDTO.getMobile(),
                email, iotUserDTO.getCompanyName(), iotUserId);
        // 保存用户信息更新日志
        IotUser user = new IotUser();
        BeanUtils.copyProperties(iotUserDTO, user);
        saveProjectLogger(user, Constants.UPDATE_OPERATE, null);
        return ResultDTO.newSuccess();
    }

    /**
     * 管理员编辑用户
     *
     * @param iotUserDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateUserByAdmin(IotUserDTO iotUserDTO) {
        Long iotUserId = iotUserDTO.getIotUserId();
        if (iotUserId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_8018.getCode(),
                    ErrorCode.ERROR_8018.getMessage());
        }
        if (!RoleCode.ADMIN.equals(iotUserDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }

        IotUser iotUser = iotUserDao.getIotUserByMobile(iotUserDTO.getMobile());
        if (iotUser != null && !iotUserId.equals(iotUser.getIotUserId())) {
            return ResultDTO.newFail(ErrorCode.ERROR_8000.getCode(),
                    ErrorCode.ERROR_8000.getMessage());
        }

        String email = Strings.nullToEmpty(iotUserDTO.getEmail());
        iotUserDao.updateUser(iotUserDTO.getModifyOperatorId(), iotUserDTO.getRealName(), iotUserDTO.getMobile(),
                email, iotUserDTO.getCompanyName(), iotUserId);

        // 保存用户信息更新日志
        IotUser user = new IotUser();
        BeanUtils.copyProperties(iotUserDTO, user);
        saveProjectLogger(user, Constants.UPDATE_OPERATE, null);

        return ResultDTO.newSuccess();
    }

    /**
     * 删除用户
     *
     * @param iotUserDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteUser(IotUserDTO iotUserDTO) {
        if (!RoleCode.ADMIN.equals(iotUserDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        List<IotUserProject> iotUserProjects =
                iotUserProjectDao.getIotUserProjectByIotUserId(iotUserDTO.getIotUserId());
        if (!CollectionUtils.isEmpty(iotUserProjects)) {
            return ResultDTO.newFail(ErrorCode.ERROR_8011.getCode(),
                    ErrorCode.ERROR_8011.getMessage());
        }
        iotUserDao.deleteUser(iotUserDTO.getIotUserId(), iotUserDTO.getModifyOperatorId());
        IotUser user = iotUserDao.findByIotUserIdAndIsDeleted(iotUserDTO.getIotUserId(), true);
        saveProjectLogger(user, Constants.DELETED_OPERATE, null);
        return ResultDTO.newSuccess();
    }

    /**
     * 查询用户列表
     *
     * @param iotUserQueryDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotUserDTO>> getUserList(IotUserQueryDTO iotUserQueryDTO) {
        if (!RoleCode.ADMIN.equals(iotUserQueryDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_8013.getCode(),
                    ErrorCode.ERROR_8013.getMessage());
        }
        Page<IotUser> page = iotUserDao.findAll((root, query, rb) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            String keywords = iotUserQueryDTO.getKeywords();
            if (StringUtils.isNotBlank(keywords)) {
                Predicate username = rb.like(root.get("username"), "%" + keywords + "%");
                Predicate realName = rb.like(root.get("realName"), "%" + keywords + "%");
                Predicate mobile = rb.like(root.get("mobile"), "%" + keywords + "%");
                Predicate companyName = rb.like(root.get("companyName"), "%" + keywords + "%");

                predicateList.add(rb.or(username, realName, mobile, companyName));
            }
            predicateList.add(rb.equal(root.get("isDeleted"), false));
            predicateList.add(rb.equal(root.get("roleCode"), RoleCode.USER));

            return rb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        }, PageRequest.of(
                iotUserQueryDTO.getPageIndex() - 1, iotUserQueryDTO.getPageSize(), Sort.Direction.DESC, "stampDate"));
        if (page.getContent().size() > 0) {
            List<IotUserDTO> iotUserDTOS = Lists.newArrayList();
            for (IotUser iotUser : page.getContent()) {
                IotUserDTO iotUserDTO = new IotUserDTO();
                BeanUtils.copyProperties(iotUser, iotUserDTO);
                iotUserDTO.setPassword(null);
                iotUserDTO.setSalt(null);
                iotUserDTO.setIotProjects(iotUserProjectDao.getProjectListByIotUserId(iotUser.getIotUserId()));
                iotUserDTOS.add(iotUserDTO);
            }
            PageResult<IotUserDTO> pageResult = new PageResult<>((int) page.getTotalElements(), iotUserDTOS);
            return ResultDTO.newSuccess(pageResult);
        } else {
            PageResult<IotUserDTO> pageResult = new PageResult<>(0, Lists.newArrayList());
            return ResultDTO.newSuccess(pageResult);
        }
    }

    /**
     * 查询协作人员列表
     *
     * @param iotUserQueryDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotUserDTO> getCollaborators(IotUserQueryDTO iotUserQueryDTO) {
        if (StringUtils.isEmpty(iotUserQueryDTO.getKeywords())) {
            return ResultDTO.newSuccess(null);
        }
        Long iotUserId = iotUserQueryDTO.getModifyOperatorId();
        IotUserDTO iotUserDTO = iotUserDao.getCollaborators(iotUserId, RoleCode.ADMIN, iotUserQueryDTO.getKeywords());
        return ResultDTO.newSuccess(iotUserDTO);
    }

    /**
     * 通过用户ID获取登录用户信息
     *
     * @param iotUserQueryDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotUserDTO> getUserById(IotUserQueryDTO iotUserQueryDTO) {
        Long iotUserId = iotUserQueryDTO.getModifyOperatorId();
        IotUser iotUser = iotUserDao.findByIotUserIdAndIsDeleted(iotUserId, false);
        if (iotUser == null) {
            return ResultDTO.newSuccess(null);
        }
        IotUserDTO userDTO = new IotUserDTO();
        BeanUtils.copyProperties(iotUser, userDTO);
        userDTO.setPassword(null);
        if (StringUtils.isNotBlank(iotUser.getAuthority())) {
            userDTO.setAuthority(JSON.parseArray(iotUser.getAuthority(), IotAuthorityDTO.class));
        }
        userDTO.setIotProjects(iotUserProjectDao.getProjectListByIotUserId(iotUserId));
        return ResultDTO.newSuccess(userDTO);
    }

    /**
     * 管理员获取普通用户信息
     *
     * @param iotUserQueryDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotUserDTO> getUserByAdminAndIotUserId(IotUserQueryDTO iotUserQueryDTO) {
        if (!RoleCode.ADMIN.equals(iotUserQueryDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        iotUserQueryDTO.setModifyOperatorId(iotUserQueryDTO.getIotUserId());
        return getUserById(iotUserQueryDTO);
    }

    /**
     * 修改密码
     *
     * @param iotUserDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updatePassword(IotUserDTO iotUserDTO) {
        Long iotUserId = iotUserDTO.getModifyOperatorId();
        String newPassword = iotUserDTO.getNewPassword();
        String password = iotUserDTO.getPassword();

        if (!newPassword.equals(iotUserDTO.getPassword2())) {
            return ResultDTO.newFail(ErrorCode.ERROR_8017.getCode(),
                    ErrorCode.ERROR_8017.getMessage());
        }
        IotUser iotUser = iotUserDao.findByIotUserIdAndIsDeleted(iotUserId, false);
        if (iotUser == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_8015.getCode(),
                    ErrorCode.ERROR_8015.getMessage());
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        password = DigestUtils.md5DigestAsHex(password.concat(iotUser.getSalt()).getBytes());
        if (!iotUser.getPassword().equals(password)) {
            return ResultDTO.newFail(ErrorCode.ERROR_8016.getCode(),
                    ErrorCode.ERROR_8016.getMessage());
        }
        String salt = UUID.randomUUID().toString();
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        newPassword = DigestUtils.md5DigestAsHex(newPassword.concat(salt).getBytes());
        iotUserDao.updatePassword(iotUserId, newPassword, salt);
        return ResultDTO.newSuccess();
    }

    /**
     * 启用禁用
     *
     * @param iotUserDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateUserStatus(IotUserDTO iotUserDTO) {
        if (!RoleCode.ADMIN.equals(iotUserDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        iotUserDao.updateUserStatus(iotUserDTO.getIotUserId(), iotUserDTO.getModifyOperatorId(),
                iotUserDTO.getEnabled());

        IotUser iotUser = iotUserDao.findByIotUserIdAndIsDeleted(iotUserDTO.getIotUserId(), false);
        saveProjectLogger(iotUser, Constants.UPDATE_OPERATE, iotUserDTO.getEnabled());
        return ResultDTO.newSuccess();
    }

    /**
     * 权限判断
     *
     * @param iotAuthorityDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<Integer> getAdminAuthority(IotAuthorityDTO iotAuthorityDTO) {
        String url = iotAuthorityDTO.getUrl();
        if (StringUtils.isBlank(url)) {
            return ResultDTO.newFail(ErrorCode.ERROR_5056.getCode(),
                    ErrorCode.ERROR_5056.getMessage());
        }
        List<String> allAuthority = Lists.newArrayList();
        List<IotUser> iotUserList = iotUserDao.getAdminAuthority(RoleCode.ADMIN);
        if (CollectionUtils.isEmpty(iotUserList)) {
            return ResultDTO.newFail(ErrorCode.ERROR_5057.getCode(),
                    ErrorCode.ERROR_5057.getMessage());
        }
        //获取所有管理员全部权限
        for (IotUser iotUser : iotUserList) {
            if (StringUtils.isBlank(iotUser.getAuthority())) {
                continue;
            }
            List<IotAuthorityDTO> iotAuthorityDTOList = JSON.parseArray(iotUser.getAuthority(), IotAuthorityDTO.class);
            if (CollectionUtils.isEmpty(iotAuthorityDTOList)) {
                continue;
            }
            for (IotAuthorityDTO authorityDTO : iotAuthorityDTOList) {
                if (!allAuthority.contains(authorityDTO.getUrl())) {
                    allAuthority.add(authorityDTO.getUrl());
                }
            }
        }

        if (!allAuthority.contains(url)) {
            return ResultDTO.newSuccess();
        }

        IotUser iotUser = iotUserDao.findByIotUserIdAndIsDeleted(iotAuthorityDTO.getModifyOperatorId(), false);
        if (iotUser == null || StringUtils.isBlank(iotUser.getAuthority())) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        List<IotAuthorityDTO> iotAuthorityDTOList = JSON.parseArray(iotUser.getAuthority(), IotAuthorityDTO.class);
        if (CollectionUtils.isEmpty(iotAuthorityDTOList)) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        List<String> authority = Lists.newArrayList();
        for (IotAuthorityDTO authorityDTO : iotAuthorityDTOList) {
            authority.add(authorityDTO.getUrl());
        }
        if (!authority.contains(url)) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        return ResultDTO.newSuccess();
    }

    /**
     * 保存设备类型操作日志
     *
     * @param iotUser
     * @param operate
     */
    private void saveProjectLogger(IotUser iotUser, int operate, Boolean enabled) {
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setLoggerTime(System.currentTimeMillis());
        logDTO.setAssociationId(iotUser.getIotUserId());
        logDTO.setIotProjectId(0L);
        logDTO.setCreateOperatorId(iotUser.getCreateOperatorId());
        logDTO.setModifyOperatorId(iotUser.getModifyOperatorId());
        logDTO.setAssociationType(IotUser.class.getSimpleName());
        logDTO.setUserId(iotUser.getCreateOperatorId());
        logDTO.setLogTypeCode(Constants.SYSTEM_LOGGER_TYPE);
        logDTO.setUsername(iotUser.getUsername());
        switch (operate) {
            case Constants.CREATE_OPERATE:
                logDTO.setLogContent("用户【" + iotUser.getUsername() + "】注册成功");
                producer.sendToQueue(logDTO);
                break;
            case Constants.UPDATE_OPERATE:
                if (enabled == null) {
                    logDTO.setLogContent("用户【" + iotUser.getUsername() + "】信息修改成功");
                } else if (enabled) {
                    logDTO.setLogContent("用户【" + iotUser.getUsername() + "】启用");
                } else {
                    logDTO.setLogContent("用户【" + iotUser.getUsername() + "】禁用");
                }
                producer.sendToQueue(logDTO);
                break;
            case Constants.DELETED_OPERATE:
                logDTO.setLogContent("用户【" + iotUser.getUsername() + "】信息删除成功");
                producer.sendToQueue(logDTO);
                break;
            default:
                break;
        }
    }
}
