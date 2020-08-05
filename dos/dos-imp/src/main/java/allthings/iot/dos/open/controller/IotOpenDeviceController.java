package allthings.iot.dos.open.controller;

import allthings.iot.dos.client.open.IotDeviceOpenApi;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
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
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.open.api.IotOpenDeviceService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 18:20:06
 */
@RestController
public class IotOpenDeviceController implements IotDeviceOpenApi {

    @Autowired
    private IotOpenDeviceService openDeviceService;

    @Override
    public ResultDTO<PageResult<IotDeviceEventDTO>> getEventList(@RequestBody IotEventQueryDTO iotEventQueryDTO) {
        return openDeviceService.getEventList(iotEventQueryDTO);
    }

    @Override
    public ResultDTO<List<IotFactorQueryDTO>> getFactorListByDeviceTypeCode(@RequestParam("deviceTypeCode") String deviceTypeCode,
                                                                            @RequestParam("iotProjectId") Long iotProjectId) {
        return openDeviceService.getFactorListByDevcieTypeCode(deviceTypeCode, iotProjectId);
    }

    @Override
    public ResultDTO<PageResult<IotLogDTO>> getLogList(@RequestBody IotLogQueryDTO iotLogQueryDTO) {
        return openDeviceService.getLogList(iotLogQueryDTO);
    }

    @Override
    public ResultDTO<Integer> updateDeviceStatus(@RequestParam("deviceCodes") String[] deviceCodes, @RequestParam(
            "status") Integer status,
                                                 @RequestParam("iotProjectId") Long iotProjectId) {
        return openDeviceService.updateDeviceStatus(Lists.newArrayList(deviceCodes), status, iotProjectId);
    }

    @Override
    public ResultDTO<List<IotDeviceStatusQueryDTO>> getDeviceStatusBatch(@RequestParam("deviceCodes") String[] deviceCodes,
                                                                         @RequestParam("iotProjectId") Long iotProjectId) {
        return openDeviceService.getDeviceStatusBatch(Lists.newArrayList(deviceCodes), iotProjectId);
    }

    @Override
    public ResultDTO<Integer> saveOrUpdateDevice(@RequestBody IotDeviceOpenDTO iotDeviceOpenDTO) {
        return openDeviceService.saveOrUpdateDevice(iotDeviceOpenDTO);
    }

    @Override
    public ResultDTO<IotOpenApiResponseDeviceDTO> saveOrUpdateDeviceOpenApi(@RequestBody IotDeviceOpenDTO iotDeviceOpenDTO) {
        return openDeviceService.saveOrUpdateDeviceOpenApi(iotDeviceOpenDTO);
    }

    @Override
    public ResultDTO<List<IotOpenApiResponseDeviceDTO>> saveOrUpdateDeviceBatch(@RequestBody IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO) {
        return openDeviceService.saveOrUpdateDeviceBatch(iotDeviceSaveBatchDTO.getDevices(),
                iotDeviceSaveBatchDTO.getIotProjectId());
    }

    @Override
    public ResultDTO<PageResult<IotOpenDeviceDTO>> getDeviceList(@RequestBody IotDeviceListQueryDTO iotDeviceListQueryDTO) {
        return openDeviceService.getDeviceList(iotDeviceListQueryDTO);
    }

    @Override
    public ResultDTO<Integer> deleteDeviceType(@RequestBody IotDeviceTypeDeleteByCodeDTO iotDeviceTypeDeleteByCodeDTO) {
        return openDeviceService.deleteDeviceType(iotDeviceTypeDeleteByCodeDTO);
    }

    @Override
    public ResultDTO<Integer> saveOrUpdateDeviceType(@RequestBody IotDeviceTypeSaveDTO iotDeviceTypeSaveDTO) {
        return openDeviceService.saveOrUpdateDeviceType(iotDeviceTypeSaveDTO);
    }

    @Override
    public ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> getDeviceTypeList(@RequestBody IotOpenDeviceTypeListQueryDTO iotOpenDeviceTypeListQueryDTO) {
        return openDeviceService.getDeviceTypeList(iotOpenDeviceTypeListQueryDTO);
    }

}
