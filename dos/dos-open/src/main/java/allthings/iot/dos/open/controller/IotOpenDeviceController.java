package allthings.iot.dos.open.controller;


import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.open.IotDeviceOpenApi;
import allthings.iot.dos.client.open.IotProjectOpenApi;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.open.IotDeviceListQueryDTO;
import allthings.iot.dos.dto.open.IotDeviceOpenDTO;
import allthings.iot.dos.dto.open.IotDeviceSaveBatchDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeDeleteByCodeDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeSaveDTO;
import allthings.iot.dos.dto.open.IotEventQueryDTO;
import allthings.iot.dos.dto.open.IotLogQueryDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceTypeListQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotOpenDeviceController
 * @CreateDate :  2018/11/16
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
@RestController
public class IotOpenDeviceController extends IotOpenBaseController {

    @Autowired
    private IotProjectOpenApi iotProjectOpenApi;

    @Autowired
    private IotDeviceOpenApi iotDeviceOpenApi;

    /**
     * 获取事件列表
     *
     * @param deviceCode
     * @param disposeStatus
     * @param endDatetime
     * @param startDatetime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/dmp/event/list")
    public ResultDTO getEventList(@RequestParam("deviceCode") String deviceCode,
                                  @RequestParam(value = "disposeStatus", required = false) Boolean disposeStatus,
                                  @RequestParam("endDatetime") Long endDatetime,
                                  @RequestParam("startDatetime") Long startDatetime,
                                  @RequestParam("pageIndex") Integer pageIndex,
                                  @RequestParam("pageSize") Integer pageSize) {
        IotEventQueryDTO iotEventQueryDTO = new IotEventQueryDTO();
        iotEventQueryDTO.setDeviceCode(deviceCode);
        iotEventQueryDTO.setDisposeStatus(disposeStatus);
        iotEventQueryDTO.setEndDatetime(endDatetime);
        iotEventQueryDTO.setStartDatetime(startDatetime);
        iotEventQueryDTO.setPageIndex(pageIndex);
        iotEventQueryDTO.setPageSize(pageSize);

        String clientId = getClientId();
        ResultDTO<Long> projectRet = iotProjectOpenApi.hasDevice(clientId, new String[]{deviceCode}, true);
        if (!projectRet.isSuccess()) {
            return projectRet;
        }

        ResultDTO<PageResult<IotDeviceEventDTO>> bizReturn =
                iotDeviceOpenApi.getEventList(iotEventQueryDTO);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        PageResult<IotDeviceEventDTO> pageResult = bizReturn.getData();
        return ResultDTO.newSuccess(pageResult.getData(), pageResult.getTotal());
    }

    /**
     * 通过设备类型编码获取因子列表
     *
     * @param deviceTypeCode
     * @return
     */
    @GetMapping("/dmp/devicetype/factors/list")
    public ResultDTO getFactorListByDevcieTypeCode(@RequestParam("deviceTypeCode") String deviceTypeCode) {
        String clientId = getClientId();
        ResultDTO<Long> projectRet = iotProjectOpenApi.hasDeviceType(clientId, new String[]{deviceTypeCode});
        if (!projectRet.isSuccess()) {
            return projectRet;
        }
        Long iotProjectId = projectRet.getData();

        return iotDeviceOpenApi.getFactorListByDeviceTypeCode(deviceTypeCode, iotProjectId);
    }

    /**
     * 获取日志列表
     *
     * @param deviceCode
     * @param endDatetime
     * @param startDatetime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/dmp/log/list")
    public ResultDTO getLogList(@RequestParam("deviceCode") String deviceCode,
                                @RequestParam("endDatetime") Long endDatetime,
                                @RequestParam("startDatetime") Long startDatetime,
                                @RequestParam("pageIndex") Integer pageIndex,
                                @RequestParam("pageSize") Integer pageSize) {
        SecurityContextHolder.getContext().getAuthentication();
        IotLogQueryDTO iotLogQueryDTO = new IotLogQueryDTO();
        iotLogQueryDTO.setDeviceCode(deviceCode);
        iotLogQueryDTO.setEndDatetime(endDatetime);
        iotLogQueryDTO.setPageIndex(pageIndex);
        iotLogQueryDTO.setPageSize(pageSize);
        iotLogQueryDTO.setStartDatetime(startDatetime);

        String clientId = getClientId();
        ResultDTO<Long> projectRet = iotProjectOpenApi.hasDevice(clientId, new String[]{deviceCode}, true);
        if (!projectRet.isSuccess()) {
            return projectRet;
        }

        ResultDTO<PageResult<IotLogDTO>> bizReturn = iotDeviceOpenApi.getLogList(iotLogQueryDTO);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        PageResult<IotLogDTO> pageResult = bizReturn.getData();
        return ResultDTO.newSuccess(pageResult.getData(), pageResult.getTotal());
    }

    /**
     * 批量修改设备启用状态
     *
     * @param deviceCodes
     * @param status
     * @return
     */
    @GetMapping("/dmp/device/status/update")
    public ResultDTO updateDeviceStatus(@RequestParam("deviceCodes") List<String> deviceCodes,
                                        @RequestParam("status") Integer status) {
        String[] deviceCodeArr = deviceCodes.toArray(new String[deviceCodes.size()]);
        ResultDTO<Long> projectRet = iotProjectOpenApi.hasDevice(getClientId(), deviceCodeArr, false);
        if (!projectRet.isSuccess()) {
            return projectRet;
        }

        return iotDeviceOpenApi.updateDeviceStatus(deviceCodeArr, status, projectRet.getData());
    }

    /**
     * 批量获取设备状态
     *
     * @param deviceCodes
     * @return
     */
    @GetMapping("/dmp/device/status/get")
    public ResultDTO getDeviceStatusBatch(@RequestParam("deviceCodes") List<String> deviceCodes) {
        String[] deviceCodeArr = deviceCodes.toArray(new String[deviceCodes.size()]);
        ResultDTO<Long> projectRet = iotProjectOpenApi.hasDevice(getClientId(), deviceCodeArr, true);
        if (!projectRet.isSuccess()) {
            return projectRet;
        }

        return iotDeviceOpenApi.getDeviceStatusBatch(deviceCodeArr, projectRet.getData());
    }

    /**
     * 新增/修改设备信息
     *
     * @param iotDeviceOpenDTO
     * @return
     */
    @PostMapping("/dmp/device/merge")
    public ResultDTO saveOrUpdateDevice(@RequestBody IotDeviceOpenDTO iotDeviceOpenDTO) {
        String clientId = getClientId();
        ResultDTO<Long> bizReturn = iotProjectOpenApi.getIotProject(clientId);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        iotDeviceOpenDTO.setIotProjectId(bizReturn.getData());
        return iotDeviceOpenApi.saveOrUpdateDeviceOpenApi(iotDeviceOpenDTO);
    }

    /**
     * 批量新增/修改设备信息
     *
     * @param iotDeviceSaveBatchDTO
     * @return
     */
    @PostMapping("/dmp/device/merge/batch")
    public ResultDTO saveOrUpdateDevice(@RequestBody IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO) {
        String clientId = getClientId();
        ResultDTO<Long> bizReturn = iotProjectOpenApi.getIotProject(clientId);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        Long iotProjectId = bizReturn.getData();
        iotDeviceSaveBatchDTO.setIotProjectId(iotProjectId);
        ResultDTO<?> ret = iotDeviceOpenApi.saveOrUpdateDeviceBatch(iotDeviceSaveBatchDTO);
        if (!ret.isSuccess()) {
            ResultDTO resultDTO = ResultDTO.newSuccess(ret.getData());
            resultDTO.setCode(ErrorCode.ERROR_3036.getCode());
            if (ret.getData() != null) {
                resultDTO.setMsg(ErrorCode.ERROR_3036.getMessage());
            } else {
                resultDTO.setMsg(ret.getMsg());
            }
            return resultDTO;
        }
        return ret;
    }

    /**
     * 获取设备列表
     *
     * @param connectStatus
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/dmp/device/list")
    public ResultDTO getDeviceList(@RequestParam(value = "connectStatus", required = false) Integer connectStatus,
                                   @RequestParam(value = "keywords", required = false) String keywords,
                                   @RequestParam("pageIndex") Integer pageIndex,
                                   @RequestParam("pageSize") Integer pageSize) {
        IotDeviceListQueryDTO iotDeviceListQueryDTO = new IotDeviceListQueryDTO();
        if (connectStatus != null) {
            iotDeviceListQueryDTO.setConnectStatus(connectStatus);

        }
        String clientId = getClientId();
        ResultDTO<Long> ret = iotProjectOpenApi.getIotProject(clientId);
        if (!ret.isSuccess()) {
            return ret;
        }
        iotDeviceListQueryDTO.setIotProjectId(ret.getData());
        iotDeviceListQueryDTO.setKeywords(keywords);
        iotDeviceListQueryDTO.setPageIndex(pageIndex);
        iotDeviceListQueryDTO.setPageSize(pageSize);
        ResultDTO<PageResult<IotOpenDeviceDTO>> bizReturn = iotDeviceOpenApi.getDeviceList(iotDeviceListQueryDTO);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        PageResult<IotOpenDeviceDTO> pageResult = bizReturn.getData();
        return ResultDTO.newSuccess(pageResult.getData(), pageResult.getTotal());
    }

    /**
     * 删除设备类型
     *
     * @param iotDeviceTypeDeleteByCodeDTO
     * @return
     */
    @PostMapping("/dmp/devicetype/delete")
    public ResultDTO deleteDeviceType(@RequestBody IotDeviceTypeDeleteByCodeDTO iotDeviceTypeDeleteByCodeDTO) {
        String clientId = getClientId();
        List<String> deviceTypeCodeList = iotDeviceTypeDeleteByCodeDTO.getDeviceTypeCodes();
        ResultDTO<Long> projectRet = iotProjectOpenApi.hasDeviceType(clientId,
                deviceTypeCodeList.toArray(new String[deviceTypeCodeList.size()]));
        if (!projectRet.isSuccess()) {
            return projectRet;
        }
        Long iotProjectId = projectRet.getData();
        iotDeviceTypeDeleteByCodeDTO.setIotProjectId(iotProjectId);
        return iotDeviceOpenApi.deleteDeviceType(iotDeviceTypeDeleteByCodeDTO);
    }

    /**
     * 新增/修改设备类型
     *
     * @param iotDeviceTypeSaveDTO
     * @return
     */
    @PostMapping("/dmp/devicetype/merge")
    public ResultDTO saveOrUpdateDeviceType(@RequestBody IotDeviceTypeSaveDTO iotDeviceTypeSaveDTO) {
        String clientId = getClientId();
        ResultDTO<Long> bizReturn = iotProjectOpenApi.getIotProject(clientId);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        iotDeviceTypeSaveDTO.setIotProjectId(bizReturn.getData());
        return iotDeviceOpenApi.saveOrUpdateDeviceType(iotDeviceTypeSaveDTO);
    }

    /**
     * 获取设备类型列表
     *
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/dmp/devicetype/list")
    public ResultDTO getDeviceTypeList(@RequestParam(value = "keywords", required = false) String keywords,
                                       @RequestParam("pageIndex") Integer pageIndex,
                                       @RequestParam("pageSize") Integer pageSize) {
        String clientId = getClientId();
        ResultDTO<Long> ret = iotProjectOpenApi.getIotProject(clientId);
        if (!ret.isSuccess()) {
            return ret;
        }

        IotOpenDeviceTypeListQueryDTO iotOpenDeviceTypeListQueryDTO = new IotOpenDeviceTypeListQueryDTO();
        iotOpenDeviceTypeListQueryDTO.setIotProjectId(ret.getData());
        if (!StringUtils.isEmpty(keywords)) {
            iotOpenDeviceTypeListQueryDTO.setKeywords("%" + keywords + "%");
        }

        iotOpenDeviceTypeListQueryDTO.setPageIndex(pageIndex);
        iotOpenDeviceTypeListQueryDTO.setPageSize(pageSize);
        ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> bizReturn =
                iotDeviceOpenApi.getDeviceTypeList(iotOpenDeviceTypeListQueryDTO);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        PageResult<IotDeviceTypeSimpleDTO> pageResult = bizReturn.getData();
        return ResultDTO.newSuccess(pageResult.getData(), pageResult.getTotal());
    }
}
