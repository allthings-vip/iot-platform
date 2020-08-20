package allthings.iot.dos.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.IotMessageCenterIdConfig;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotLoggerService;
import allthings.iot.dos.api.IotMessageService;
import allthings.iot.dos.api.IotProjectService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.constant.ProjectStatus;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.consumer.IotLoggerProducer;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotDeviceTypeDao;
import allthings.iot.dos.dao.IotProjectDao;
import allthings.iot.dos.dao.IotProjectDeviceTypeDao;
import allthings.iot.dos.dao.IotProjectQueryDao;
import allthings.iot.dos.dao.IotTagDao;
import allthings.iot.dos.dao.IotUserDao;
import allthings.iot.dos.dao.IotUserProjectDao;
import allthings.iot.dos.dao.oauth2.IotOauthClientDetailsDao;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.IotUserProjectDTO;
import allthings.iot.dos.dto.query.IotAppSecretQueryDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import allthings.iot.dos.dto.query.IotProjectDeleteQueryDTO;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import allthings.iot.dos.model.IotProject;
import allthings.iot.dos.model.IotUserProject;
import allthings.iot.dos.model.oauth2.IotOauthClientDetails;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author :  luhao
 * @FileName :  IotProjectBizImpl
 * @CreateDate :  2018/5/4
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
@Service(value = "iotProjectService")
public class IotProjectServiceImpl implements IotProjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotProjectServiceImpl.class);
    @Autowired
    private IotProjectDao iotProjectDao;
    @Autowired
    private IotUserProjectDao iotUserProjectDao;
    @Autowired
    private IotDeviceDao iotDeviceDao;
    @Autowired
    private IotProjectDeviceTypeDao iotProjectDeviceTypeDao;
    @Autowired
    private IotDeviceTypeDao iotDeviceTypeDao;
    @Autowired
    private IotTagDao iotTagDao;
    @Autowired
    private IotUserDao iotUserDao;
    @Autowired
    private IotOauthClientDetailsDao iotOauthClientDetailsDao;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;
    @Autowired
    private IotProjectQueryDao iotProjectQueryDao;
    @Autowired
    private IotMessageService messageService;
    @Autowired
    private IotMessageCenterIdConfig config;
    @Autowired
    private IotLoggerService iotLoggerService;
    @Autowired
    private IotLoggerProducer producer;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> saveIotProject(IotProjectDTO iotProjectDTO) {
        Long createOperatorId = iotProjectDTO.getCreateOperatorId();
        Long[] createOperatorIds = {createOperatorId};

        Long iotProjectId = iotProjectDao.getIotProjectIdByProjectName(iotProjectDTO.getProjectName(),
                iotProjectDTO.getCreateOperatorId());
        if (iotProjectId != null) {
            LOGGER.error(" project name {} duplicate ", iotProjectDTO.getProjectName());
            return ResultDTO.newFail(ErrorCode.ERROR_2019.getCode(),
                    ErrorCode.ERROR_2019.getMessage());
        }
        ResultDTO<Integer> bizReturn = validateIotUserId(iotProjectDTO.getIotUserIds(), createOperatorId);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        IotProject iotProject = iotProjectDao.saveAndFlush(getIotProject(iotProjectDTO));
        iotProjectId = iotProject.getIotProjectId();

        iotUserProjectDao.saveAll(getIotUserProject(iotProjectId, createOperatorIds, createOperatorId, true));
        if (ArrayUtils.isNotEmpty(iotProjectDTO.getIotUserIds())) {
            iotUserProjectDao.saveAll(getIotUserProject(iotProjectId, iotProjectDTO.getIotUserIds(), createOperatorId
                    , false));
        }

        // 保存项目操作日志
        saveProjectLogger(iotProject, Constants.CREATE_OPERATE);

        return ResultDTO.newSuccess(iotProject.getIotProjectId());
    }

    /**
     * 验证协作人员是否有创建者和管理员
     *
     * @param iotUserIds
     * @param createOperatorId
     * @return
     */
    private ResultDTO<Integer> validateIotUserId(Long[] iotUserIds, Long createOperatorId) {

        if (ArrayUtils.isEmpty(iotUserIds)) {
            return ResultDTO.newSuccess();
        }
        List<Long> list = Lists.newArrayList(iotUserIds);
        if (list.contains(createOperatorId)) {
            return ResultDTO.newFail(ErrorCode.ERROR_2008.getCode(),
                    ErrorCode.ERROR_2008.getMessage());
        }
        Long count = iotUserDao.getAdminCount(iotUserIds, RoleCode.ADMIN);
        if (count > 0) {
            return ResultDTO.newFail(ErrorCode.ERROR_2008.getCode(),
                    ErrorCode.ERROR_2008.getMessage());
        }
        return ResultDTO.newSuccess();
    }

    /**
     * 组装IotProject
     *
     * @param iotProjectDTO
     * @return
     */
    private IotProject getIotProject(IotProjectDTO iotProjectDTO) {
        IotProject iotProject = new IotProject();
        if (iotProjectDTO.getIotProjectId() != null) {
            iotProject.setIotProjectId(iotProjectDTO.getIotProjectId());
        }
        iotProject.setImageUrl(Strings.nullToEmpty(iotProjectDTO.getImageUrl()));
        iotProject.setDescription(Strings.nullToEmpty(iotProjectDTO.getDescription()));
        iotProject.setProjectName(iotProjectDTO.getProjectName());
        iotProject.setCompanyName(Strings.nullToEmpty(iotProjectDTO.getCompanyName()));
        iotProject.setCreateOperatorId(iotProjectDTO.getCreateOperatorId());
        iotProject.setModifyOperatorId(iotProjectDTO.getCreateOperatorId());
        iotProject.setReview(false);
        iotProject.setClientId(Strings.nullToEmpty(iotProjectDTO.getClientId()));

        return iotProject;
    }

    /**
     * 组装IotUserProject
     *
     * @param iotProjectId
     * @param iotUserIds
     * @param createOperatorId
     * @param isOwner
     * @return
     */
    private List<IotUserProject> getIotUserProject(Long iotProjectId, Long[] iotUserIds, Long createOperatorId,
                                                   Boolean isOwner) {
        List<IotUserProject> iotUserProjects = Lists.newArrayList();
        for (Long iotUserId : iotUserIds) {
            IotUserProject iotUserProject = new IotUserProject();
            iotUserProject.setIotProjectId(iotProjectId);
            iotUserProject.setIotUserId(iotUserId);
            iotUserProject.setCreateOperatorId(createOperatorId);
            iotUserProject.setModifyOperatorId(createOperatorId);
            iotUserProject.setOwner(isOwner);
            iotUserProjects.add(iotUserProject);
        }

        return iotUserProjects;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteIotProject(IotProjectDeleteQueryDTO iotProjectDeleteQueryDTO) {

        Long[] iotProjectIds = iotProjectDeleteQueryDTO.getIotProjectIds();
        String roleCode = iotProjectDeleteQueryDTO.getRoleCode();
        Long modifyOperatorId = iotProjectDeleteQueryDTO.getModifyOperatorId();
        if (ArrayUtils.isEmpty(iotProjectIds)) {
            LOGGER.error(ErrorCode.ERROR_2004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }


        StringBuffer sb = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
        for (int index = 0; index < iotProjectIds.length; index++) {
            Long iotProjectId = iotProjectIds[index];
            List<IotUserProjectDTO> iotUserProjectDTOS = iotUserProjectDao.getIotUserByProjectId(iotProjectId,
                    true);
            if (CollectionUtils.isEmpty(iotUserProjectDTOS)) {
                return ResultDTO.newFail(ErrorCode.ERROR_2005.getCode(),
                        ErrorCode.ERROR_2005.getMessage());
            }
            IotUserProjectDTO iotUserProjectDTO = iotUserProjectDTOS.get(0);
            if (!modifyOperatorId.equals(iotUserProjectDTO.getIotUserId()) && !RoleCode.ADMIN.equals(roleCode)) {
                if (index == 0) {
                    sb.append(iotUserProjectDTO.getProjectName());
                } else {
                    sb.append("、").append(iotUserProjectDTO.getProjectName());
                }
            }
            if (iotUserProjectDTO.getReview()) {
                if (index == 0) {
                    stringBuffer.append(iotUserProjectDTO.getProjectName());
                } else {
                    stringBuffer.append("、").append(iotUserProjectDTO.getProjectName());
                }
            }
        }
        if (StringUtils.isNotBlank(sb)) {
            return ResultDTO.newFail(ErrorCode.ERROR_2013.getCode(),
                    String.format(ErrorCode.ERROR_2013.
                            getMessage(), sb.toString()));
        }
        // 删除逻辑修改，无设备项目可删除
//        if (StringUtils.isNotBlank(stringBuffer)) {
//            return ResultDTO.newFail(new BizError(ErrorCode.ERROR_2022.getCode(),
//                    String.format(ErrorCode.ERROR_2022.
//                    getMessage(), stringBuffer.toString())));
//        }


        List<Long> iotProjectIdList = Lists.newArrayList(iotProjectIds);

        //判断是否有设备
        List<IotProjectSimpleDTO> iotProjectSimpleDTOList =
                iotDeviceDao.getIotProjectDeviceByIotProjectId(iotProjectIdList);
        if (iotProjectSimpleDTOList.size() > 0) {
            String name = getDeleteProjectName(iotProjectSimpleDTOList);
            return ResultDTO.newFail(ErrorCode.ERROR_2018.getCode(),
                    String.format(ErrorCode.ERROR_2018.
                            getMessage(), name));
        }

        //判断是否有设备类型
        List<IotProjectSimpleDTO> iotProjectSimpleDTOS =
                iotDeviceTypeDao.getIotProjectDeviceTypeByIotProjectId(iotProjectIdList);
        if (!CollectionUtils.isEmpty(iotProjectSimpleDTOS)) {
            String name = getDeleteProjectName(iotProjectSimpleDTOS);
            return ResultDTO.newFail(ErrorCode.ERROR_2017.getCode(),
                    String.format(ErrorCode.ERROR_2017.
                            getMessage(), name));
        }

        //判断是否有标签
        List<IotProjectSimpleDTO> projectSimpleDTOS = iotTagDao.getIotTagByIotProjectId(iotProjectIdList);
        if (!CollectionUtils.isEmpty(projectSimpleDTOS)) {
            String name = getDeleteProjectName(projectSimpleDTOS);
            return ResultDTO.newFail(ErrorCode.ERROR_2016.getCode(),
                    String.format(ErrorCode.ERROR_2016.
                            getMessage(), name));
        }

        for (Long iotProjectId : iotProjectIdList) {
            iotProjectDao.deleteByIotProjectId(iotProjectId, modifyOperatorId);
            iotUserProjectDao.deleteUserProjectByIotProjectId(iotProjectId, modifyOperatorId);
        }

        for (Long iotProjectId : iotProjectIdList) {
            IotProject iotProject = iotProjectDao.getIotProjectByIotProjectId(iotProjectId);
            saveProjectLogger(iotProject, Constants.DELETED_OPERATE);
        }

        return ResultDTO.newSuccess();
    }

    /**
     * 获取要删除的项目名称
     *
     * @param iotProjectSimpleDTOList
     * @return
     */
    private String getDeleteProjectName(List<IotProjectSimpleDTO> iotProjectSimpleDTOList) {
        StringBuffer sb = new StringBuffer();
        for (int index = 0; index < iotProjectSimpleDTOList.size(); index++) {
            IotProjectSimpleDTO iotProjectSimpleDTO = iotProjectSimpleDTOList.get(index);
            if (index == 0) {
                sb.append(iotProjectSimpleDTO.getProjectName());
            } else {
                sb.append("、");
                sb.append(iotProjectSimpleDTO.getProjectName());
            }
        }
        return sb.toString();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateIotProject(IotProjectDTO iotProjectDTO) {
        List<IotUserProjectDTO> iotUserProjectDTOS = iotUserProjectDao.getIotUserByProjectId(iotProjectDTO.
                getIotProjectId(), true);
        if (CollectionUtils.isEmpty(iotUserProjectDTOS)) {
            return ResultDTO.newFail(ErrorCode.ERROR_2005.getCode(),
                    ErrorCode.ERROR_2005.getMessage());
        }
        IotUserProjectDTO iotUserProjectDTO = iotUserProjectDTOS.get(0);
        Long modifyOperatorId = iotProjectDTO.getModifyOperatorId();

        if (!modifyOperatorId.equals(iotUserProjectDTO.getIotUserId()) && !RoleCode.ADMIN.equals(iotProjectDTO.
                getRoleCode())) {

            return ResultDTO.newFail(ErrorCode.ERROR_2013.getCode(),
                    String.format(ErrorCode.ERROR_2013.
                            getMessage(), iotUserProjectDTO.getProjectName()));
        }

        Long iotProjectId = iotProjectDao.getIotProjectIdByProjectName(iotProjectDTO.getProjectName(),
                iotUserProjectDTO.getIotUserId());
        if (iotProjectId != null && !iotProjectId.equals(iotProjectDTO.getIotProjectId())) {
            LOGGER.error(" project name {} duplicate ", iotProjectDTO.getProjectName());
            return ResultDTO.newFail(ErrorCode.ERROR_2019.getCode(),
                    ErrorCode.ERROR_2019.getMessage());
        }
        ResultDTO<Integer> bizReturn = validateIotUserId(iotProjectDTO.getIotUserIds(),
                iotUserProjectDTO.getIotUserId());
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }

        String companyName = Strings.nullToEmpty(iotProjectDTO.getCompanyName());
        String imgUrl = Strings.nullToEmpty(iotProjectDTO.getImageUrl());
        String description = Strings.nullToEmpty(iotProjectDTO.getDescription());
        iotProjectDao.updateIotProject(iotProjectDTO.getIotProjectId(), iotProjectDTO.getProjectName(),
                imgUrl, description, companyName, modifyOperatorId);
        iotUserProjectDao.deleteUserProject(iotProjectDTO.getIotProjectId(), modifyOperatorId);

        if (ArrayUtils.isNotEmpty(iotProjectDTO.getIotUserIds())) {
            iotUserProjectDao.saveAll(getIotUserProject(iotProjectDTO.getIotProjectId(),
                    iotProjectDTO.getIotUserIds(), modifyOperatorId, false));
        }

        // 保存更新项目操作日志
        IotProject iotProject = new IotProject();
        BeanUtils.copyProperties(iotProjectDTO, iotProject);
        saveProjectLogger(iotProject, Constants.UPDATE_OPERATE);

        return ResultDTO.newSuccess();
    }


    /**
     * 查询项目列表
     *
     * @param iotProjectSimpleDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotProjectQueryDTO>> getIotProjectList(IotProjectSimpleDTO iotProjectSimpleDTO) {
        String projectName = iotProjectSimpleDTO.getProjectName();
        Integer pageIndex = iotProjectSimpleDTO.getPageIndex();
        Integer pageSize = iotProjectSimpleDTO.getPageSize();
        Long iotUserId = iotProjectSimpleDTO.getModifyOperatorId();
        String roleCode = iotProjectSimpleDTO.getRoleCode();
        long start = System.currentTimeMillis();
        QueryResult<IotProjectQueryDTO> queryResult;
        if (RoleCode.ADMIN.equals(roleCode)) {
            Page<IotProject> page = iotProjectDao.findAll((root, query, rb) -> {
                List<Predicate> predicateList = Lists.newArrayList();
                if (StringUtils.isNotEmpty(projectName)) {
                    predicateList.add(rb.like(root.get("projectName"), "%" + projectName + "%"));
                }
                predicateList.add(rb.equal(root.get("isDeleted"), false));

                return rb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }, PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "stampDate"));
            queryResult = new QueryResult<>(getIotProjectQueryDTOList(page.getContent()), page.getTotalElements());
        } else {
            queryResult = iotProjectQueryDao.getIotProjectList(iotUserId, projectName, pageIndex, pageSize);
        }

        if (queryResult != null && queryResult.getRowCount() > 0) {
            List<IotProjectQueryDTO> iotProjectQueryDTOList = queryResult.getItems();
            for (IotProjectQueryDTO iotProjectQueryDTO : iotProjectQueryDTOList) {
                iotProjectQueryDTO.setDeviceTypes(iotProjectDeviceTypeDao.getIotDeviceTypeByIotProjectIdAndDeleted
                        (iotProjectQueryDTO.getIotProjectId()));
                //项目设备数量
                List<String> deviceCodeList =
                        iotDeviceDao.getDeviceCodeByIotProjectId(iotProjectQueryDTO.getIotProjectId());
                iotProjectQueryDTO.setDeviceTotal((long) deviceCodeList.size());
                iotProjectQueryDTO.setCreateOperator(iotUserDao.getIotUserByIotUserId(iotProjectQueryDTO.getCreateOperatorId()));

                Boolean status = iotProjectQueryDTO.getReview();
                if (status) {
                    iotProjectQueryDTO.setStatus(ProjectStatus.AUDITED);
                } else {
                    iotProjectQueryDTO.setStatus(ProjectStatus.UNREVIEWED);
                }
            }
            PageResult<IotProjectQueryDTO> pageResult = new PageResult<>((int) queryResult.getRowCount(),
                    iotProjectQueryDTOList);

            LOGGER.warn("cost time {}", System.currentTimeMillis() - start);
            return ResultDTO.newSuccess(pageResult);
        } else {
            PageResult<IotProjectQueryDTO> pageResult = new PageResult<>(0, Lists.newArrayList());
            return ResultDTO.newSuccess(pageResult);
        }
    }

    private List<IotProjectQueryDTO> getIotProjectQueryDTOList(List<IotProject> iotProjects) {
        List<IotProjectQueryDTO> iotProjectQueryDTOList = Lists.newArrayList();
        for (IotProject iotProject : iotProjects) {
            IotProjectQueryDTO iotProjectQueryDTO = new IotProjectQueryDTO();
            BeanUtils.copyProperties(iotProject, iotProjectQueryDTO);
            iotProjectQueryDTOList.add(iotProjectQueryDTO);
        }
        return iotProjectQueryDTOList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotProjectSimpleDTO>> getIotProjectNameList(IotProjectSimpleDTO iotProjectSimpleDTO) {
        if (!RoleCode.ADMIN.equals(iotProjectSimpleDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage());
        }
        List<IotProjectSimpleDTO> iotProjectSimpleDTOList = iotProjectDao.getProjectNameByDeleted(false);
        return ResultDTO.newSuccess(iotProjectSimpleDTOList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotProjectDTO> getIotProjectDetail(IotProjectSimpleDTO iotProjectSimpleDTO) {
        Long iotProjectId = iotProjectSimpleDTO.getIotProjectId();
        Long iotUserId = iotProjectSimpleDTO.getModifyOperatorId();
        String roleCode = iotProjectSimpleDTO.getRoleCode();
        IotProjectDTO iotProjectDTO = null;
        if (RoleCode.ADMIN.equals(roleCode)) {
            iotProjectDTO = iotProjectDao.getByIotProjectIdAndDeleted(iotProjectId, false);
        } else {
            iotProjectDTO = iotProjectDao.getByIotProjectIdAndIotUserId(iotProjectId, iotUserId);
        }

        if (iotProjectDTO == null) {
            LOGGER.error(ErrorCode.ERROR_2005.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_2005.getCode(),
                    ErrorCode.ERROR_2005.getMessage());
        }
        List<IotUserQueryDTO> iotUserQueryDTOS = iotUserProjectDao.getIotUserListByProjectId(iotProjectId);
        iotProjectDTO.setIotUserList(iotUserQueryDTOS);

        return ResultDTO.newSuccess(iotProjectDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> reviewProject(IotProjectDTO iotProjectDTO) {
        if (!RoleCode.ADMIN.equals(iotProjectDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_2020.getCode(),
                    ErrorCode.ERROR_2020.getMessage());
        }
        Long iotProjectId = iotProjectDTO.getIotProjectId();
        String clientId = System.currentTimeMillis() + "_" + iotProjectId;
        clientId = DigestUtils.md5DigestAsHex(clientId.getBytes());
        saveIotOauthClientDetails(clientId, iotProjectDTO.getCreateOperatorId());
        iotProjectDao.reviewProject(iotProjectId, true, clientId);
        return ResultDTO.newSuccess();
    }

    /**
     * 保存client info信息
     *
     * @param clientId
     */
    private void saveIotOauthClientDetails(String clientId, Long createOperatorId) {
        IotOauthClientDetails clientDetails = new IotOauthClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(RandomStringUtils.randomAlphanumeric(32));
        clientDetails.setAccessTokenValidity(7200);
        clientDetails.setRefreshTokenValidity(7200);
        clientDetails.setAdditionalInformation("");
        clientDetails.setAuthorizedGrantTypes("client_credentials");
        clientDetails.setAuthorities("");
        clientDetails.setResourceIds("");
        clientDetails.setWebServerRedirectUri("");
        clientDetails.setScope("read,write");
        clientDetails.setAutoApprove("true");
        clientDetails.setCreateOperatorId(createOperatorId);
        clientDetails.setIsDeleted(false);

        iotOauthClientDetailsDao.saveAndFlush(clientDetails);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<String> getAppSecret(IotAppSecretQueryDTO iotAppSecretQueryDTO) {
        Long iotUserId = iotAppSecretQueryDTO.getCreateOperatorId();
        Long iotProjectId = iotAppSecretQueryDTO.getIotProjectId();
        if (iotProjectId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }
        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId,
                iotAppSecretQueryDTO.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        IotMessageManagerDTO iotMessageManagerDTO = new IotMessageManagerDTO();
        iotMessageManagerDTO.setCode(iotAppSecretQueryDTO.getCode());
        iotMessageManagerDTO.setMobileNumber(iotAppSecretQueryDTO.getMobile());
        ResultDTO<Integer> result = messageService.validateIdentifyCode(iotMessageManagerDTO);
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }

        IotProjectDTO iotProjectDTO = iotProjectDao.getByIotProjectIdAndDeleted(iotProjectId, false);
        if (iotProjectDTO == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2005.getCode(),
                    ErrorCode.ERROR_2005.getMessage());
        }
        IotOauthClientDetails iotOauthClientDetails =
                iotOauthClientDetailsDao.selectClientDetails(iotProjectDTO.getClientId());
        if (iotOauthClientDetails == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2023.getCode(),
                    ErrorCode.ERROR_2023.getMessage());
        }

        return ResultDTO.newSuccess(iotOauthClientDetails.getClientSecret());
    }

    /**
     * 通过clientId查询
     *
     * @param clientId
     * @return
     */
    @Override
    public IotProjectDTO getProjectByClientId(String clientId) {
        IotProject iotProject = iotProjectDao.findByClientIdAndIsDeleted(clientId, false);
        IotProjectDTO iotProjectDTO = null;
        if (iotProject != null) {
            iotProjectDTO = new IotProjectDTO();
            BeanUtils.copyProperties(iotProject, iotProjectDTO);
        }
        return iotProjectDTO;
    }

    /**
     * 保存设备类型操作日志
     *
     * @param iotProject
     * @param operate
     */
    private void saveProjectLogger(IotProject iotProject, int operate) {
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setLoggerTime(System.currentTimeMillis());
        logDTO.setAssociationType(IotProject.class.getSimpleName());
        logDTO.setLogTypeCode(Constants.SYSTEM_LOGGER_TYPE);
        logDTO.setUserId(iotProject.getCreateOperatorId());
        logDTO.setCreateOperatorId(iotProject.getCreateOperatorId());
        logDTO.setModifyOperatorId(iotProject.getCreateOperatorId());
        logDTO.setIotProjectId(iotProject.getIotProjectId());
        logDTO.setAssociationId(iotProject.getIotProjectId());
        switch (operate) {
            case Constants.CREATE_OPERATE:
                logDTO.setLogContent("新增项目【" + iotProject.getProjectName() + "】成功");
                producer.sendToQueue(logDTO);
                break;
            case Constants.DELETED_OPERATE:
                logDTO.setLogContent("删除项目【" + iotProject.getProjectName() + "】成功");
                producer.sendToQueue(logDTO);
                break;
            case Constants.UPDATE_OPERATE:
                logDTO.setLogContent("修改项目【" + iotProject.getProjectName() + "】信息成功");
                producer.sendToQueue(logDTO);
                break;
            default:
                break;
        }
    }
}
