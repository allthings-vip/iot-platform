package allthings.iot.dos.open.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotEventService;
import allthings.iot.dos.api.IotFactorService;
import allthings.iot.dos.api.IotLogService;
import allthings.iot.dos.api.IotProtocolService;
import allthings.iot.dos.api.IotTagService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.open.IotDeviceListQueryDTO;
import allthings.iot.dos.dto.open.IotDeviceOpenDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeDeleteByCodeDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeSaveDTO;
import allthings.iot.dos.dto.open.IotEventQueryDTO;
import allthings.iot.dos.dto.open.IotLogQueryDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceTypeListQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.open.api.IotOpenDeviceService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotOpenDeviceBizImpl
 * @CreateDate :  2018/11/14
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service("iotOpenDeviceBiz")
public class IotOpenDeviceServiceImpl implements IotOpenDeviceService {

    private static Logger LOGGER = LoggerFactory.getLogger(IotOpenDeviceServiceImpl.class);

    @Autowired
    private IotEventService iotEventService;

    @Autowired
    private IotFactorService iotFactorService;

    @Autowired
    private IotLogService iotLogService;

    @Autowired
    private IotDeviceService iotDeviceService;

    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Autowired
    private IotTagService iotTagService;

    @Autowired
    private IotProtocolService iotProtocolService;

    @Autowired
    private ApplicationContext context;

    @Override
    public ResultDTO<PageResult<IotDeviceEventDTO>> getEventList(IotEventQueryDTO iotEventQueryDTO) {
        try {
            if (StringUtils.isEmpty(iotEventQueryDTO.getDeviceCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            if (iotEventQueryDTO.getStartDatetime() == null || iotEventQueryDTO.getEndDatetime() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                        ErrorCode.ERROR_5052.getMessage());
            }
            return iotEventService.getDeviceEventsByDeviceId(iotEventQueryDTO.getDeviceCode(), null,
                    iotEventQueryDTO.getStartDatetime(), iotEventQueryDTO.getEndDatetime(),
                    iotEventQueryDTO.getPageIndex(),
                    iotEventQueryDTO.getPageSize());
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<List<IotFactorQueryDTO>> getFactorListByDevcieTypeCode(String deviceTypeCode, Long iotProjectId) {
        try {
            if (StringUtils.isEmpty(deviceTypeCode)) {
                return ResultDTO.newFail(ErrorCode.ERROR_1006.getCode(),
                        ErrorCode.ERROR_1006.getMessage());
            }
            return iotFactorService.getIotFactorListByDeviceCode(deviceTypeCode, iotProjectId);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<PageResult<IotLogDTO>> getLogList(IotLogQueryDTO iotLogQueryDTO) {
        try {
            if (StringUtils.isEmpty(iotLogQueryDTO.getDeviceCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            if (iotLogQueryDTO.getStartDatetime() == null || iotLogQueryDTO.getEndDatetime() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                        ErrorCode.ERROR_5052.getMessage());
            }
            return iotLogService.getMsgLogs(iotLogQueryDTO.getDeviceCode(), null, iotLogQueryDTO.getStartDatetime(),
                    iotLogQueryDTO.getEndDatetime(), iotLogQueryDTO.getPageIndex(), iotLogQueryDTO.getPageSize(),
                    iotLogQueryDTO.getIotProjectId(), 0L, "admin");
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<Integer> updateDeviceStatus(List<String> deviceCodes, Integer status, Long iotProjectId) {
        try {
            if (CollectionUtils.isEmpty(deviceCodes)) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            if (status == null) {
                return ResultDTO.newFail("可用状态不能为空");
            }
            if (status.intValue() != 0 && status.intValue() != 1) {
                return ResultDTO.newFail("可用状态只能是0和1");
            }
            return iotDeviceService.updateIotDeviceStatus(deviceCodes, status, iotProjectId);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("更新失败");
        }
    }

    @Override
    public ResultDTO<List<IotDeviceStatusQueryDTO>> getDeviceStatusBatch(List<String> deviceCodes, Long iotProjectId) {
        try {
            if (CollectionUtils.isEmpty(deviceCodes)) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            IotDeviceStatusBatchQueryDTO batchQueryDTO = new IotDeviceStatusBatchQueryDTO();
            batchQueryDTO.setDeviceCodes(deviceCodes);
            batchQueryDTO.setIotProjectId(iotProjectId);
            batchQueryDTO.setRoleCode(RoleCode.ADMIN);
            batchQueryDTO.setCreateOperatorId(0L);
            return iotDeviceService.getIotDeviceStatus(batchQueryDTO);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("查询失败");
        }
    }


    @Override
    public ResultDTO<Integer> saveOrUpdateDevice(IotDeviceOpenDTO iotDeviceOpenDTO) {
        try {
            Long typeId = iotDeviceTypeService.getDeviceTypeIdByTypeCode(iotDeviceOpenDTO.getDeviceTypeCode(),
                    iotDeviceOpenDTO.getIotProjectId()).getData();
            if (typeId == null) {
                return ResultDTO.newFail("设备类型不存在");
            }
            List<Long> tagIds = new ArrayList<>();
            if (!CollectionUtils.isEmpty(iotDeviceOpenDTO.getTagName())) {
                for (String name : iotDeviceOpenDTO.getTagName()) {
                    Long tagId = iotTagService.getIdByTagName(name, iotDeviceOpenDTO.getIotProjectId()).getData();
                    if (tagId == null) {
                        return ResultDTO.newFail("标签[" + name + "]不存在");
                    }
                    tagIds.add(tagId);
                }
            }
            iotDeviceOpenDTO.setIotTagIds(tagIds);
            iotDeviceOpenDTO.setIotDeviceTypeId(typeId);

            Long id = iotDeviceService.getIotDeviceIdByDeviceCode(iotDeviceOpenDTO.getDeviceCode(),
                    iotDeviceOpenDTO.getIotProjectId()).getData();
            if (id == null) {
                id = iotDeviceService.getIotDeviceIdByDeviceCode(iotDeviceOpenDTO.getDeviceCode(),
                        Constants.LONG_OF_NULL).getData();
            }

            IotDeviceDTO deviceDTO = new IotDeviceDTO();
            BeanUtils.copyProperties(iotDeviceOpenDTO, deviceDTO);
            if (id == null) {
                iotDeviceService.saveIotDevice(deviceDTO);
            } else {
                deviceDTO.setIotDeviceId(id);
                iotDeviceService.updateIotDevice(deviceDTO);
            }
            return ResultDTO.newSuccess();
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("操作失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<IotOpenApiResponseDeviceDTO> saveOrUpdateDeviceOpenApi(IotDeviceOpenDTO iotDeviceOpenDTO) {
        try {
            Long iotProjectId = iotDeviceOpenDTO.getIotProjectId();
            String deviceTypeCode = iotDeviceOpenDTO.getDeviceTypeCode();
            IotOpenApiResponseDeviceDTO responseDto = new IotOpenApiResponseDeviceDTO();
            responseDto.setDeviceCode(iotDeviceOpenDTO.getDeviceCode());
            Long typeId = iotDeviceTypeService.getDeviceTypeIdByTypeCode(deviceTypeCode, iotProjectId).getData();
            if (typeId == null) {
                LOGGER.warn("设备类型编码：{}在项目：{}中不存在.", deviceTypeCode, iotProjectId);
                return ResultDTO.newFail("设备类型不存在");
            }

            List<Long> tagIds = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(iotDeviceOpenDTO.getTagName())) {
                for (String name : iotDeviceOpenDTO.getTagName()) {
                    Long tagId = iotTagService.getIdByTagName(name, iotProjectId).getData();
                    if (tagId == null) {
                        LOGGER.warn("标签:{}不存在", name);
                        return ResultDTO.newFail("标签[" + name + "]不存在");
                    }
                    tagIds.add(tagId);
                }
            }

            iotDeviceOpenDTO.setIotTagIds(tagIds);
            iotDeviceOpenDTO.setIotDeviceTypeId(typeId);

            String deviceCode = iotDeviceOpenDTO.getDeviceCode();
            Long id = iotDeviceService.getIotDeviceIdByDeviceCode(deviceCode, iotProjectId).getData();
            if (id == null) {
                id = iotDeviceService.getIotDeviceIdByDeviceCode(deviceCode, Constants.LONG_OF_NULL).getData();
            }

            //同名的类
            IotDeviceDTO deviceDTO = new IotDeviceDTO();
            BeanUtils.copyProperties(iotDeviceOpenDTO, deviceDTO);
            if (id == null) {
                iotDeviceService.saveIotDevice(deviceDTO);
            } else {
                deviceDTO.setIotDeviceId(id);
                iotDeviceService.updateIotDevice(deviceDTO);
            }

            String protocolCode = iotProtocolService.getCodeByDeviceTypeCode(deviceTypeCode, iotProjectId);
            responseDto.setProtocolCode(protocolCode);
            responseDto.setNewDeviceCode(responseDto.getProtocolCode() + responseDto.getDeviceCode());
            return ResultDTO.newSuccess(responseDto);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("操作失败");
        }
    }

    @Override
    public ResultDTO<List<IotOpenApiResponseDeviceDTO>> saveOrUpdateDeviceBatch(List<IotDeviceOpenDTO> iotDeviceOpenDTOS, Long iotProjectId) {
        try {
            List<IotDeviceDTO> dtos = new ArrayList<>();
            for (IotDeviceOpenDTO iotDeviceOpenDTO : iotDeviceOpenDTOS) {
                Long typeId = iotDeviceTypeService.getDeviceTypeIdByTypeCode(iotDeviceOpenDTO.getDeviceTypeCode(),
                        iotProjectId).getData();
                if (typeId == null) {
                    return ResultDTO.newFail("设备类型不存在:" + iotDeviceOpenDTO.getDeviceTypeCode());
                }
                IotDeviceDTO deviceDTO = new IotDeviceDTO();
                BeanUtils.copyProperties(iotDeviceOpenDTO, deviceDTO);
                deviceDTO.setIotProjectId(iotProjectId);
                deviceDTO.setIotDeviceTypeId(typeId);
                dtos.add(deviceDTO);
            }
            ResultDTO<List<IotOpenApiResponseDeviceDTO>> bizReturn = iotDeviceService.saveOpenIotDevice(iotProjectId,
                    dtos);
            if (!bizReturn.isSuccess()) {
                return bizReturn;
            }
            List<IotOpenApiResponseDeviceDTO> ret = new ArrayList<>();
            for (IotDeviceOpenDTO dto : iotDeviceOpenDTOS) {
                IotOpenApiResponseDeviceDTO iotOpenApiResponseDeviceDTO = new IotOpenApiResponseDeviceDTO();
                iotOpenApiResponseDeviceDTO.setDeviceCode(dto.getDeviceCode());
                iotOpenApiResponseDeviceDTO.setProtocolCode(iotProtocolService.getCodeByDeviceTypeCode(dto.getDeviceTypeCode(), iotProjectId));
                iotOpenApiResponseDeviceDTO.setNewDeviceCode(
                        iotOpenApiResponseDeviceDTO.getProtocolCode() + iotOpenApiResponseDeviceDTO.getDeviceCode());
                ret.add(iotOpenApiResponseDeviceDTO);
            }
            return ResultDTO.newSuccess(ret);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("操作失败");
        }
    }

    @Override
    public ResultDTO<PageResult<IotOpenDeviceDTO>> getDeviceList(IotDeviceListQueryDTO iotDeviceListQueryDTO) {
        try {
            if (iotDeviceListQueryDTO.getPageSize() == null || iotDeviceListQueryDTO.getPageIndex() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5053.getCode(),
                        ErrorCode.ERROR_5053.getMessage());
            }
            ResultDTO<PageResult<IotDeviceQueryDTO>> bizReturn =
                    iotDeviceService.getIotOpenDeviceList(iotDeviceListQueryDTO.getConnected(),
                            iotDeviceListQueryDTO.getIotProjectId(), iotDeviceListQueryDTO.getKeywords(),
                            iotDeviceListQueryDTO.getPageIndex(), iotDeviceListQueryDTO.getPageSize(), true);
            if (!bizReturn.isSuccess()) {
                return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
            }
            if (bizReturn.getData() == null) {
                return ResultDTO.newSuccess(new PageResult<>(0, Lists.newArrayList()));
            }
            PageResult<IotDeviceQueryDTO> pageResult = bizReturn.getData();
            List<IotDeviceQueryDTO> data = pageResult.getData();
            List<IotOpenDeviceDTO> ret = new ArrayList<>();
            for (IotDeviceQueryDTO dto : data) {
                IotOpenDeviceDTO iotOpenDeviceDTO = new IotOpenDeviceDTO();
                iotOpenDeviceDTO.setBizCode(dto.getBizCode());
                iotOpenDeviceDTO.setConnectStatus(dto.getConnected());
                iotOpenDeviceDTO.setDeviceCode(dto.getDeviceCode());
                iotOpenDeviceDTO.setDeviceName(dto.getDeviceName());
                iotOpenDeviceDTO.setInputDate(dto.getInputDate());
                iotOpenDeviceDTO.setLatestConnectDatetime(dto.getLatestConnectDatetime());
                iotOpenDeviceDTO.setLatestUploadDatetime(dto.getLatestUploadDatetime());
                iotOpenDeviceDTO.setProjectName(dto.getProjectName());
                iotOpenDeviceDTO.setStatus(dto.getEnabled());
                iotOpenDeviceDTO.setAgencyName(dto.getAgencyName());
                iotOpenDeviceDTO.setNewDeviceCode(dto.getProtocolCode() + dto.getDeviceCode());
                ret.add(iotOpenDeviceDTO);
            }
            return ResultDTO.newSuccess(new PageResult<>(pageResult.getTotal(), ret));
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<Integer> deleteDeviceType(IotDeviceTypeDeleteByCodeDTO iotDeviceTypeDeleteByCodeDTO) {
        try {
            if (CollectionUtils.isEmpty(iotDeviceTypeDeleteByCodeDTO.getDeviceTypeCodes())) {
                return ResultDTO.newSuccess();
            }
            IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO = new IotDeviceTypeDeleteDTO();
            iotDeviceTypeDeleteDTO.setDevieTypeCodes(iotDeviceTypeDeleteByCodeDTO.getDeviceTypeCodes());
            iotDeviceTypeDeleteDTO.setIotProjectId(iotDeviceTypeDeleteByCodeDTO.getIotProjectId());
            return iotDeviceTypeService.deleteIotDeviceTypeByCode(iotDeviceTypeDeleteDTO);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("删除失败");
        }
    }

    @Override
    public ResultDTO<Integer> saveOrUpdateDeviceType(IotDeviceTypeSaveDTO iotDeviceTypeSaveDTO) {
        try {
            if (StringUtils.isEmpty(iotDeviceTypeSaveDTO.getDeviceTypeCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_1006.getCode(),
                        ErrorCode.ERROR_1006.getMessage());
            }
            if (StringUtils.isEmpty(iotDeviceTypeSaveDTO.getDeviceTypeName())) {
                return ResultDTO.newFail(ErrorCode.ERROR_1008.getCode(),
                        ErrorCode.ERROR_1008.getMessage());
            }
            if (StringUtils.isEmpty(iotDeviceTypeSaveDTO.getProtocolCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_7007.getCode(),
                        ErrorCode.ERROR_7007.getMessage());
            }
            Long protocolId = iotProtocolService.getIdByCode(iotDeviceTypeSaveDTO.getProtocolCode());
            if (protocolId == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_7005.getCode(),
                        ErrorCode.ERROR_7005.getMessage());
            }
            Long typeId = iotDeviceTypeService.getDeviceTypeIdByTypeCode(iotDeviceTypeSaveDTO.getDeviceTypeCode(),
                    iotDeviceTypeSaveDTO.getIotProjectId()).getData();
            dealSaveDTO(iotDeviceTypeSaveDTO);
            IotDeviceTypeDTO iotDeviceTypeDTO = new IotDeviceTypeDTO();
            BeanUtils.copyProperties(iotDeviceTypeSaveDTO, iotDeviceTypeDTO);
            iotDeviceTypeDTO.setIotProtocolId(protocolId);
            iotDeviceTypeDTO.setCreateOperatorId(0L);
            iotDeviceTypeDTO.setModifyOperatorId(0L);
            iotDeviceTypeDTO.setRoleCode("admin");
            if (typeId == null) {
                typeId = iotDeviceTypeService.getDeviceTypeIdByTypeName(iotDeviceTypeSaveDTO.getDeviceTypeName(),
                        iotDeviceTypeSaveDTO.getIotProjectId()).getData();
                if (typeId == null) {
                    ResultDTO<Long> bizReturn = iotDeviceTypeService.saveIotDeviceType(iotDeviceTypeDTO);
                    return ResultDTO.newSuccess(Integer.valueOf(bizReturn.getData().toString()));
                }
                return ResultDTO.newFail(ErrorCode.ERROR_7025.getCode(),
                        ErrorCode.ERROR_7025.getMessage());
            }

            iotDeviceTypeDTO.setIotDeviceTypeId(typeId);
            return iotDeviceTypeService.updateIotDeviceType(iotDeviceTypeDTO);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("操作失败");
        }
    }

    private void dealSaveDTO(IotDeviceTypeSaveDTO iotDeviceTypeSaveDTO) {
        if (StringUtils.isEmpty(iotDeviceTypeSaveDTO.getDescription())) {
            iotDeviceTypeSaveDTO.setDescription("");
        }
        if (StringUtils.isEmpty(iotDeviceTypeSaveDTO.getImageUrl())) {
            iotDeviceTypeSaveDTO.setImageUrl("");
        }
    }

    @Override
    public ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> getDeviceTypeList(IotOpenDeviceTypeListQueryDTO iotOpenDeviceTypeListQueryDTO) {
        if (iotOpenDeviceTypeListQueryDTO.getPageSize() == null ||
                iotOpenDeviceTypeListQueryDTO.getPageIndex() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5053.getCode(),
                    ErrorCode.ERROR_5053.getMessage());
        }
        try {
            IotProjectSimpleDTO iotProjectSimpleDTO = new IotProjectSimpleDTO();
            iotProjectSimpleDTO.setIotProjectId(iotOpenDeviceTypeListQueryDTO.getIotProjectId());
            iotProjectSimpleDTO.setKeywords(iotOpenDeviceTypeListQueryDTO.getKeywords());
            iotProjectSimpleDTO.setPageIndex(iotOpenDeviceTypeListQueryDTO.getPageIndex());
            iotProjectSimpleDTO.setPageSize(iotOpenDeviceTypeListQueryDTO.getPageSize());
            iotProjectSimpleDTO.setIotProjectId(iotOpenDeviceTypeListQueryDTO.getIotProjectId());
            return iotDeviceTypeService.getOpenIotDeviceTypeList(iotProjectSimpleDTO);
        } catch (Exception e) {
            LOGGER.error("异常：{}", e);
            return ResultDTO.newFail("查询失败");
        }
    }


}
