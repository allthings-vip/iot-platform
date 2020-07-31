package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.consumer.IotLoggerProducer;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotDeviceTypeDao;
import allthings.iot.dos.dao.IotProjectDao;
import allthings.iot.dos.dao.IotProjectDeviceTypeDao;
import allthings.iot.dos.dao.IotProtocolDao;
import allthings.iot.dos.dao.IotUserDao;
import allthings.iot.dos.dao.IotUserProjectDao;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.model.IotDeviceType;
import allthings.iot.dos.model.IotProject;
import allthings.iot.dos.model.IotProtocol;
import allthings.iot.dos.model.IotUserProject;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotLoggerService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import allthings.iot.common.dto.PageResult;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeBizImpl
 * @CreateDate :  2018/4/29
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
@Service("iotDeviceTypeService")
public class IotDeviceTypeServiceImpl implements IotDeviceTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceTypeServiceImpl.class);
    @Autowired
    private IotDeviceTypeDao iotDeviceTypeDao;
    @Autowired
    private IotProjectDeviceTypeDao iotProjectDeviceTypeDao;
    @Autowired
    private IotProtocolDao iotProtocolDao;
    @Autowired
    private IotUserProjectDao iotUserProjectDao;
    @Autowired
    private IotDeviceDao iotDeviceDao;
    @Autowired
    private IotUserDao iotUserDao;
    @Autowired
    private IotProjectDao iotProjectDao;
    @Autowired
    private IotLoggerService iotLoggerService;
    @Autowired
    private IotLoggerProducer producer;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateIotDeviceType(IotDeviceTypeDTO iotDeviceTypeDTO) {

        LOGGER.info("UPDATE deviceType----->>" + JSON.toJSONString(iotDeviceTypeDTO));
        Long iotProjectId = iotDeviceTypeDTO.getIotProjectId();

        ResultDTO<Integer> result = judgeProject(iotProjectId, iotDeviceTypeDTO.getModifyOperatorId(),
                iotDeviceTypeDTO.getRoleCode());
        if (!result.isSuccess()) {
            return result;
        }

        if (StringUtils.isEmpty(iotDeviceTypeDTO.getDeviceTypeName())) {
            LOGGER.error("device type name is null", iotDeviceTypeDTO.getDeviceTypeName());
            return ResultDTO.newFail(ErrorCode.ERROR_1008.getCode(),
                    ErrorCode.ERROR_1008.getMessage());
        }

        Long vIotDeviceTypeId = iotDeviceTypeDao.getIotDeviceTypeIdByDeviceTypeName(iotDeviceTypeDTO
                .getDeviceTypeName(), iotProjectId);
        if (vIotDeviceTypeId != null && !vIotDeviceTypeId.equals(iotDeviceTypeDTO.getIotDeviceTypeId())) {
            LOGGER.error("device type name {} duplicate", iotDeviceTypeDTO.getDeviceTypeName());
            return ResultDTO.newFail(ErrorCode.ERROR_1020.getCode(),
                    ErrorCode.ERROR_1020.getMessage());
        }

        IotDeviceType deviceType =
                iotDeviceTypeDao.getIotDeviceTypeByIotDeviceTypeId(iotDeviceTypeDTO.getIotDeviceTypeId(), iotProjectId);
        if (deviceType.getIotProtocolId() != null &&
                !deviceType.getIotProtocolId().equals(iotDeviceTypeDTO.getIotProtocolId())) {
            return ResultDTO.newFail(ErrorCode.ERROR_7025.getCode(),
                    ErrorCode.ERROR_7025.getMessage());
        }

        String description = Strings.nullToEmpty(iotDeviceTypeDTO.getDescription());
        String imgUrl = Strings.nullToEmpty(iotDeviceTypeDTO.getImageUrl());
        String manufacturer = Strings.nullToEmpty(iotDeviceTypeDTO.getManufacturer());
        Integer updateCount = iotDeviceTypeDao.updateIotDeviceType(iotDeviceTypeDTO.getIotDeviceTypeId(), iotProjectId,
                iotDeviceTypeDTO.getDeviceTypeName(), description, imgUrl,
                iotDeviceTypeDTO.getModifyOperatorId(), manufacturer);
        if (updateCount > 0) {
            // 保存项目类型日志
            saveProjectLogger(iotDeviceTypeDTO, Constants.UPDATE_OPERATE);
            return ResultDTO.newSuccess();
        } else {
            LOGGER.error("device type update error,iotDeviceTypeId={},error={}", iotDeviceTypeDTO
                    .getIotDeviceTypeId(), ErrorCode.ERROR_1005.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_1005.getCode(),
                    ErrorCode.ERROR_1005.getMessage());
        }

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> saveIotDeviceType(IotDeviceTypeDTO iotDeviceTypeDTO) {
        LOGGER.info("新增设备类型------>>" + JSON.toJSONString(iotDeviceTypeDTO));
        Long iotProjectId = iotDeviceTypeDTO.getIotProjectId();

        ResultDTO<Integer> result = judgeProject(iotProjectId, iotDeviceTypeDTO.getCreateOperatorId(),
                iotDeviceTypeDTO.getRoleCode());
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }
        Long vIotDeviceTypeId =
                iotDeviceTypeDao.getIotDeviceTypeIdByDeviceTypeCode(iotDeviceTypeDTO.getDeviceTypeCode(),
                iotProjectId);
        if (vIotDeviceTypeId != null) {
            LOGGER.error("device type code {} duplicate", iotDeviceTypeDTO.getDeviceTypeCode());
            return ResultDTO.newFail(ErrorCode.ERROR_1019.getCode(),
                    ErrorCode.ERROR_1019.getMessage());
        }

        vIotDeviceTypeId = iotDeviceTypeDao.getIotDeviceTypeIdByDeviceTypeName(iotDeviceTypeDTO.getDeviceTypeName(),
                iotProjectId);
        if (vIotDeviceTypeId != null) {
            LOGGER.error("device type name {} duplicate", iotDeviceTypeDTO.getDeviceTypeName());
            return ResultDTO.newFail(ErrorCode.ERROR_1020.getCode(),
                    ErrorCode.ERROR_1020.getMessage());
        }

        IotProtocol iotProtocol = iotProtocolDao.getByIotProtocolId(iotDeviceTypeDTO.getIotProtocolId());
        if (iotProtocol == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_7005.getCode(),
                    ErrorCode.ERROR_7005.getMessage());
        }

        IotDeviceType iotDeviceType = getIotDeviceType(iotDeviceTypeDTO);
        iotDeviceType = iotDeviceTypeDao.saveAndFlush(iotDeviceType);

        // 保存项目日志操作
        iotDeviceTypeDTO.setIotDeviceTypeId(iotDeviceType.getIotDeviceTypeId());
        saveProjectLogger(iotDeviceTypeDTO, Constants.CREATE_OPERATE);

        return ResultDTO.newSuccess(iotDeviceType.getIotDeviceTypeId());
    }


    /**
     * 构建设备类型模型
     *
     * @param iotDeviceTypeDTO
     * @return
     */
    private IotDeviceType getIotDeviceType(IotDeviceTypeDTO iotDeviceTypeDTO) {
        IotDeviceType iotDeviceType = new IotDeviceType();
        if (iotDeviceTypeDTO.getIotDeviceTypeId() != null) {
            iotDeviceType.setIotDeviceTypeId(iotDeviceTypeDTO.getIotDeviceTypeId());
        }
        iotDeviceType.setDescription(Strings.nullToEmpty(iotDeviceTypeDTO.getDescription()));
        iotDeviceType.setDeviceTypeCode(iotDeviceTypeDTO.getDeviceTypeCode());
        iotDeviceType.setDeviceTypeName(iotDeviceTypeDTO.getDeviceTypeName());
        iotDeviceType.setImageUrl(Strings.nullToEmpty(iotDeviceTypeDTO.getImageUrl()));
        iotDeviceType.setIotProtocolId(iotDeviceTypeDTO.getIotProtocolId());
        iotDeviceType.setIotProjectId(iotDeviceTypeDTO.getIotProjectId());
        iotDeviceType.setEnabled(true);
        iotDeviceType.setCreateOperatorId(iotDeviceTypeDTO.getCreateOperatorId());
        iotDeviceType.setModifyOperatorId(iotDeviceTypeDTO.getModifyOperatorId());
        iotDeviceType.setManufacturer(Strings.nullToEmpty(iotDeviceTypeDTO.getManufacturer()));

        return iotDeviceType;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateIotDeviceTypeStatus(Long[] iotDeviceTypeIds, String operator, Integer status) {
        if (ArrayUtils.isEmpty(iotDeviceTypeIds)) {
            LOGGER.error("device type update error", ErrorCode.ERROR_1004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_1004.getCode(),
                    ErrorCode.ERROR_1004.getMessage());
        }

        if (status == null) {
            LOGGER.error("device type update error", ErrorCode.ERROR_1017.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_1017.getCode(),
                    ErrorCode.ERROR_1017.getMessage());
        }

        boolean isEnabled;
        if (status == 0) {
            isEnabled = false;
        } else {
            isEnabled = true;
        }


        for (Long iotDeviceTypeId : iotDeviceTypeIds) {
            iotDeviceTypeDao.updateIotDeviceTypeStatus(iotDeviceTypeId, operator, isEnabled, !isEnabled);
        }

        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteIotDeviceType(IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO) {
        Long[] iotDeviceTypeIds = iotDeviceTypeDeleteDTO.getIotDeviceTypeIds();
        Long iotUserId = iotDeviceTypeDeleteDTO.getModifyOperatorId();
        Long iotProjectId = iotDeviceTypeDeleteDTO.getIotProjectId();
        String roleCode = iotDeviceTypeDeleteDTO.getRoleCode();
        if (ArrayUtils.isEmpty(iotDeviceTypeIds)) {
            LOGGER.error("device type delete error", ErrorCode.ERROR_1004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_1004.getCode(),
                    ErrorCode.ERROR_1004.getMessage());
        }

        if (iotProjectId == null) {
            LOGGER.error("device type delete error", ErrorCode.ERROR_2004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }

        ResultDTO<Integer> result = judgeProject(iotProjectId, iotUserId, roleCode);
        if (!result.isSuccess()) {
            return result;
        }
        for (Long iotDeviceTypeId : iotDeviceTypeIds) {
            Long iotDeviceCount = iotDeviceDao.getCountByIotProjectIdAndIotDeviceTypeId(iotProjectId, iotDeviceTypeId);
            if (iotDeviceCount > 0) {
                return ResultDTO.newFail(ErrorCode.ERROR_1023.getCode(),
                        ErrorCode.ERROR_1023.getMessage());
            }
        }
        for (Long iotDeviceTypeId : iotDeviceTypeIds) {
            IotDeviceType deviceType = iotDeviceTypeDao.getIotDeviceTypeByIotDeviceTypeId(iotDeviceTypeId,
                    iotProjectId);
            iotDeviceTypeDao.deleteByIotDeviceTypeId(iotDeviceTypeId, iotProjectId, iotUserId);
            // 保存删除设备类型日志
            IotDeviceTypeDTO deviceTypeDTO = new IotDeviceTypeDTO();
            BeanUtils.copyProperties(deviceType, deviceTypeDTO);
            saveProjectLogger(deviceTypeDTO, Constants.DELETED_OPERATE);
        }

        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteIotDeviceTypeByCode(IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO) {
        if (!CollectionUtils.isEmpty(iotDeviceTypeDeleteDTO.getDevieTypeCodes())) {
            List<String> deviceTypeCodes = iotDeviceTypeDeleteDTO.getDevieTypeCodes();
            Long iotProjectId = iotDeviceTypeDeleteDTO.getIotProjectId();
            for (String deviceTypeCode : deviceTypeCodes) {
                Long iotDeviceCount = iotDeviceDao.getCountByIotProjectIdAndDeviceTypeCode(iotProjectId,
                        deviceTypeCode);
                if (iotDeviceCount > 0) {
                    return ResultDTO.newFail(ErrorCode.ERROR_1023.getCode(),
                            ErrorCode.ERROR_1023.getMessage());
                }
            }
            List<IotDeviceType> deviceTypes = iotDeviceTypeDao.getIotDeviceTypeByDeviceTypeCodes(deviceTypeCodes,
                    iotProjectId);
            iotDeviceTypeDao.deleteIotDeviceTypeByCode(iotDeviceTypeDeleteDTO.getDevieTypeCodes(),
                    iotDeviceTypeDeleteDTO.getIotProjectId());
            // 保存删除设备类型日志
            for (IotDeviceType deviceType : deviceTypes) {
                IotDeviceTypeDTO deviceTypeDTO = new IotDeviceTypeDTO();
                BeanUtils.copyProperties(deviceType, deviceTypeDTO);
                saveProjectLogger(deviceTypeDTO, Constants.DELETED_OPERATE);
            }
        }
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotDeviceTypeQueryDTO>> getIotDeviceTypeList(IotProjectSimpleDTO iotProjectSimpleDTO) {
        String keywords = iotProjectSimpleDTO.getKeywords();
        Integer pageIndex = iotProjectSimpleDTO.getPageIndex();
        Integer pageSize = iotProjectSimpleDTO.getPageSize();
        Long iotUserId = iotProjectSimpleDTO.getModifyOperatorId();
        String roleCode = iotProjectSimpleDTO.getRoleCode();
        Long iotProjectId = iotProjectSimpleDTO.getIotProjectId();

        ResultDTO<Integer> result = judgeProject(iotProjectId, iotUserId, roleCode);
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }

        //查询IotDeviceType
        Page<IotDeviceType> page = iotDeviceTypeDao.findAll((root, query, rb) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (StringUtils.isNotEmpty(keywords)) {
                predicateList.add(rb.or(rb.like(root.get("deviceTypeCode"), "%" + keywords + "%"),
                        rb.like(root.get("deviceTypeName"), "%" + keywords + "%"),
                        rb.like(root.get("manufacturer"), "%" + keywords + "%")));
            }
            predicateList.add(rb.equal(root.get("iotProjectId"), iotProjectId));
            predicateList.add(rb.equal(root.get("isDeleted"), false));

            return rb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        }, PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "stampDate"));


        if (page.getContent().size() > 0) {
            List<IotDeviceType> iotDeviceTypeList = page.getContent();
            List<IotDeviceTypeQueryDTO> iotDeviceTypeQueryDTOList = Lists.newArrayList();
            for (IotDeviceType iotDeviceType : iotDeviceTypeList) {
                IotDeviceTypeQueryDTO iotDeviceTypeQueryDTO = new IotDeviceTypeQueryDTO();
                BeanUtils.copyProperties(iotDeviceType, iotDeviceTypeQueryDTO);

                IotProtocol iotProtocol = iotProtocolDao.getByIotProtocolId(iotDeviceType.getIotProtocolId());
                if (iotProtocol != null) {
                    iotDeviceTypeQueryDTO.setProtocolName(iotProtocol.getProtocolName());
                }

                iotDeviceTypeQueryDTO.setUsername(iotUserDao.getIotUserByIotUserId(iotDeviceType.getCreateOperatorId()));
                iotDeviceTypeQueryDTOList.add(iotDeviceTypeQueryDTO);
            }

            PageResult<IotDeviceTypeQueryDTO> pageResult = new PageResult<>((int) page.getTotalElements(),
                    iotDeviceTypeQueryDTOList);
            return ResultDTO.newSuccess(pageResult);
        } else {

            PageResult<IotDeviceTypeQueryDTO> pageResult = new PageResult<>(0, Lists.newArrayList());
            return ResultDTO.newSuccess(pageResult);
        }
    }

    @Override
    public ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> getOpenIotDeviceTypeList(IotProjectSimpleDTO iotProjectSimpleDTO) {
        Pageable pageable = PageRequest.of(iotProjectSimpleDTO.getPageIndex() - 1, iotProjectSimpleDTO.getPageSize());
        Page<IotDeviceTypeSimpleDTO> iotDeviceTypeSimpleDtos =
                iotDeviceTypeDao.getOpenIotDeviceTypeList(iotProjectSimpleDTO.getIotProjectId(),
                "%" + (iotProjectSimpleDTO.getKeywords() == null ? "" : iotProjectSimpleDTO.getKeywords()) +
                        "%", pageable);
        PageResult<IotDeviceTypeSimpleDTO> pageResult =
                new PageResult<>(Math.toIntExact(iotDeviceTypeSimpleDtos.getTotalElements()),
                iotDeviceTypeSimpleDtos.getContent());
        return ResultDTO.newSuccess(pageResult);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotDeviceTypeDTO> getIotDeviceTypeDetail(IotDeviceTypeDTO iotDeviceTypeDTO) {
        Long iotDeviceTypeId = iotDeviceTypeDTO.getIotDeviceTypeId();
        Long iotProjectId = iotDeviceTypeDTO.getIotProjectId();
        if (iotDeviceTypeId == null) {
            LOGGER.error("device type detail error {}", ErrorCode.ERROR_1004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_1004.getCode(),
                    ErrorCode.ERROR_1004.getMessage());
        }

        if (iotProjectId == null) {
            LOGGER.error("device type detail error {}", ErrorCode.ERROR_2004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }

        ResultDTO<Integer> result = judgeProject(iotProjectId, iotDeviceTypeDTO.getCreateOperatorId(),
                iotDeviceTypeDTO.getRoleCode());
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }


        IotDeviceType iotDeviceType = iotDeviceTypeDao.getIotDeviceTypeByIotDeviceTypeId(iotDeviceTypeId, iotProjectId);
        if (iotDeviceType == null) {
            LOGGER.error("device type query error,iotDeviceTypeId={},error={}", iotDeviceTypeId, ErrorCode
                    .ERROR_1005.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_1005.getCode(),
                    ErrorCode.ERROR_1005.getMessage());
        }
        IotProtocol iotProtocol = iotProtocolDao.getByIotProtocolId(iotDeviceType.getIotProtocolId());

        IotDeviceTypeDTO deviceTypeDTO = new IotDeviceTypeDTO();
        BeanUtils.copyProperties(iotDeviceType, deviceTypeDTO);
        if (iotProtocol != null) {
            deviceTypeDTO.setProtocolName(iotProtocol.getProtocolName());
            deviceTypeDTO.setIotProtocolId(iotProtocol.getIotProtocolId());
        }

        return ResultDTO.newSuccess(deviceTypeDTO);
    }

    /**
     * 根据项目获取设备类型
     *
     * @param iotDeviceTypeDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotProjectDeviceTypeDTO>> getIotDeviceTypeByIotProjectId(IotDeviceTypeDTO iotDeviceTypeDTO) {
        Long iotProjectId = iotDeviceTypeDTO.getIotProjectId();
        Long iotUserId = iotDeviceTypeDTO.getModifyOperatorId();
        String roleCode = iotDeviceTypeDTO.getRoleCode();
        ResultDTO<Integer> bizReturn = judgeProject(iotProjectId, iotUserId, roleCode);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        List<IotProjectDeviceTypeDTO> resultList = iotProjectDeviceTypeDao.getIotDeviceTypeByIotProjectIdAndDeleted
                (iotProjectId);
        return ResultDTO.newSuccess(resultList);
    }

    /**
     * 判断项目是否通过审核和用户是否有权限
     *
     * @param iotProjectId
     * @param roleCode
     * @param userId
     * @return
     */
    @Override
    public ResultDTO<Integer> judgeProject(Long iotProjectId, Long userId, String roleCode) {
        if (!RoleCode.ADMIN.equals(roleCode)) {
            IotUserProject iotUserProject = iotUserProjectDao.getIotUserProjectByIotUserIdAndIotProjectId(
                    userId, iotProjectId);
            if (iotUserProject == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_1022.getCode(),
                        ErrorCode.ERROR_1022.getMessage());
            }
        }
        IotProject iotProject = iotProjectDao.findByIotProjectIdAndIsDeleted(iotProjectId, false);
        if (iotProject == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2005.getCode(),
                    ErrorCode.ERROR_2005.getMessage());
        }
        if (!iotProject.getReview()) {
            return ResultDTO.newFail(ErrorCode.ERROR_2015.getCode(),
                    ErrorCode.ERROR_2015.getMessage());
        }
        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<Long> getDeviceTypeIdByTypeCode(String deviceTypeCode, Long iotProjectId) {
        Long typeId = iotDeviceTypeDao.getIotDeviceTypeIdByDeviceTypeCode(deviceTypeCode, iotProjectId);
        return ResultDTO.newSuccess(typeId);
    }

    @Override
    public ResultDTO<Long> getDeviceTypeIdByTypeName(String deviceTypeName, Long iotProjectId) {
        Long typeId = iotDeviceTypeDao.getIotDeviceTypeIdByDeviceTypeName(deviceTypeName, iotProjectId);
        return ResultDTO.newSuccess(typeId);
    }

    /**
     * 保存设备类型操作日志
     *
     * @param deviceTypeDTO
     * @param operate
     */
    private void saveProjectLogger(IotDeviceTypeDTO deviceTypeDTO, int operate) {
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setUserId(deviceTypeDTO.getCreateOperatorId());
        logDTO.setLogTypeCode(Constants.SYSTEM_LOGGER_TYPE);
        logDTO.setAssociationType(IotDeviceType.class.getSimpleName());
        logDTO.setModifyOperatorId(deviceTypeDTO.getModifyOperatorId());
        logDTO.setCreateOperatorId(deviceTypeDTO.getCreateOperatorId());
        logDTO.setIotProjectId(deviceTypeDTO.getIotProjectId());
        logDTO.setAssociationId(deviceTypeDTO.getIotDeviceTypeId());
        logDTO.setLoggerTime(System.currentTimeMillis());
        switch (operate) {
            case Constants.CREATE_OPERATE:
                logDTO.setLogContent("新增设备类型【" + deviceTypeDTO.getDeviceTypeName() + "】成功");
                producer.sendToQueue(logDTO);
                break;
            case Constants.DELETED_OPERATE:
                logDTO.setLogContent("删除设备类型【" + deviceTypeDTO.getDeviceTypeName() + "】成功");
                producer.sendToQueue(logDTO);
                break;
            case Constants.UPDATE_OPERATE:
                logDTO.setLogContent("修改设备类型【" + deviceTypeDTO.getDeviceTypeName() + "】信息成功");
                producer.sendToQueue(logDTO);
                break;
            default:
                break;
        }
    }

}
