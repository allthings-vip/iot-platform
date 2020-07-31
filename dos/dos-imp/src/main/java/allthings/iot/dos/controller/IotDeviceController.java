package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.client.api.IotDeviceApi;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;
import allthings.iot.dos.dto.IotDeviceRegisterBatchDTO;
import allthings.iot.dos.dto.IotDeviceSaveBatchDTO;
import allthings.iot.dos.dto.IotDeviceSimpleDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotDeviceStatusDTO;
import allthings.iot.dos.dto.query.IotDeviceCountQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceCountTitleDTO;
import allthings.iot.dos.dto.query.IotDeviceDetailQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDetailQueryDto;
import allthings.iot.dos.dto.query.IotDeviceHistoryQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceListBaseQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLocationQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotDeviceMonitorQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryByCodeDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusCountDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDosDeviceEventQueryListDto;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/07/02 16:02:42
 */
@RestController
public class IotDeviceController implements IotDeviceApi {

    @Autowired
    private IotDeviceService deviceService;

    @Override
    public ResultDTO<Long> saveIotDevice(@RequestBody IotDeviceDTO iotDeviceDTO) {
        return deviceService.saveIotDevice(iotDeviceDTO);
    }

    @Override
    public ResultDTO<List<IotDeviceErrorMsgDTO>> saveIotDevice(@RequestBody IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO) {
        return deviceService.saveIotDevice(iotDeviceSaveBatchDTO);
    }

    @Override
    public ResultDTO<Long> updateIotDevice(@RequestBody IotDeviceDTO iotDeviceDTO) {
        return deviceService.updateIotDevice(iotDeviceDTO);
    }

    @Override
    public ResultDTO<Integer> updateIotDeviceStatus(@RequestBody IotDeviceStatusDTO iotDeviceStatusDTO) {
        return deviceService.updateIotDeviceStatus(iotDeviceStatusDTO);
    }

    @Override
    public ResultDTO<IotDeviceStatusCountDTO> getDeviceCountByOnlineStatus(@RequestBody IotDeviceQueryListDTO iotDeviceQueryListDTO) {
        return deviceService.getDeviceCountByOnlineStatus(iotDeviceQueryListDTO);
    }

    @Override
    public ResultDTO<List<IotDeviceCountQueryDTO>> getDeviceCountByType(@RequestBody IotDosQueryDTO iotDosQueryDTO) {
        return deviceService.getDeviceCountByType(iotDosQueryDTO);
    }

    @Override
    public ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountTopByType(@RequestParam("startDatetime") Long startDatetime,
                                                                                  @RequestParam("endDatetime") Long endDatetime,
                                                                                  @RequestParam("top") Integer top,
                                                                                  @RequestParam("type") String type) {
        return deviceService.getDeviceCountTopByType(startDatetime, endDatetime, top, type);
    }

    @Override
    public ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(@RequestBody IotDeviceDTO iotDeviceDTO) {
        return deviceService.getIotDeviceDetail(iotDeviceDTO);
    }

    @Override
    public ResultDTO<PageResult<IotDeviceQueryDTO>> getIotDeviceByIotProjectId(@RequestBody IotDeviceQueryListDTO iotDeviceQueryListDTO) {
        return deviceService.getIotDeviceByIotProjectId(iotDeviceQueryListDTO);
    }

    @Override
    public ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(@RequestBody IotDeviceQueryByCodeDTO iotDeviceQueryByCodeDTO) {
        return deviceService.getIotDeviceDetail(iotDeviceQueryByCodeDTO);
    }

    @Override
    public ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountByTag(@RequestParam("iotTagId") Long iotTagId,
                                                                              @RequestParam("startDatetime") Long startDatetime,
                                                                              @RequestParam("endDatetime") Long endDatetime) {
        return deviceService.getDeviceCountByTag(iotTagId, startDatetime, endDatetime);
    }

    @Override
    public ResultDTO<List<IotDeviceStatusQueryDTO>> getIotDeviceStatus(@RequestBody IotDeviceStatusBatchQueryDTO iotDeviceStatusBatchQueryDTO) {
        return deviceService.getIotDeviceStatus(iotDeviceStatusBatchQueryDTO);
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(@RequestBody IotDeviceDTO iotDeviceDTO) {
        return deviceService.getProtocolByDeviceCode(iotDeviceDTO.getDeviceCode());
    }

    @Override
    public ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(@RequestBody IotDeviceStatusBatchQueryDTO batchQueryDTO) {
        return deviceService.getProtocolByDeviceCodes(batchQueryDTO.getDeviceCodes());
    }

    @Override
    public ResultDTO<PageResult<IotDeviceDTO>> getIotDeviceByRegister(@RequestBody IotDeviceMonitorQueryListDTO iotDeviceMonitorQueryListDTO) {
        return deviceService.getIotDeviceByRegister(iotDeviceMonitorQueryListDTO);
    }

    @Override
    public ResultDTO<Long> updateIotDeviceRegister(@RequestBody IotDeviceDTO iotDeviceDTO) {
        iotDeviceDTO.setRegisterStatus(true);
        return deviceService.updateIotDevice(iotDeviceDTO);
    }

    @Override
    public ResultDTO<Integer> updateIotDeviceRegisterBatch(@RequestBody IotDeviceRegisterBatchDTO iotDeviceRegisterBatchDTO) {
        return deviceService.updateIotDeviceRegisterBatch(iotDeviceRegisterBatchDTO);
    }

    @Override
    public ResultDTO<List<IotDeviceQueryDTO>> getDeviceLocation(@RequestBody IotDeviceLocationQueryDTO iotDeviceLocationQueryDTO) {
        return deviceService.getDeviceLocation(iotDeviceLocationQueryDTO);
    }

    @Override
    public ResultDTO<List<Map<String, String>>> history(@RequestBody IotDeviceHistoryQueryDTO iotDeviceHistoryQueryDTO) {
        return deviceService.history(iotDeviceHistoryQueryDTO);
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(@RequestBody IotDeviceDTO iotDeviceDTO) {
        return deviceService.getDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(), iotDeviceDTO.getIotProjectId());
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        return deviceService.getDeviceIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceId(@RequestParam("deviceId") String deviceId) {
        return deviceService.getIotProjectIdByDeviceId(deviceId);
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        return deviceService.getIotProjectIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<List<IotDeviceSimpleDTO>> getSimpleDevices() {
        return deviceService.getSimpleDevices();
    }

    @Override
    public ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        return deviceService.getIotDeviceIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventList(@RequestBody IotDosDeviceEventQueryListDto deviceDTO) {
        return deviceService.getDeviceEventList(deviceDTO);
    }

    @Override
    public ResultDTO<IotDesEventDetailDto> getDeviceEventDetail(@RequestBody IotDeviceEventDetailQueryDto deviceEventDetailQueryDto) {
        return deviceService.getDeviceEventDetail(deviceEventDetailQueryDto);
    }

    @Override
    public ResultDTO getDeviceLoggerListByIotDeviceId(@RequestBody IotDeviceLoggerQueryListDto deviceLoggerQueryListDto) {
        return deviceService.getDeviceLoggerListByIotDeviceId(deviceLoggerQueryListDto);
    }

    @Override
    public ResultDTO<String> getDeviceIdByBizCode(@RequestParam("bizCode") String bizCode) {
        return deviceService.getDeviceIdByBizCode(bizCode);
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(@RequestParam("bizCode") String bizCode) {
        return deviceService.getProtocolByBizCode(bizCode);
    }

    @Override
    public ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(@RequestParam("deviceCode") String deviceCode,
                                                            @RequestParam("iotProjectId") Long iotProjectId) {
        return deviceService.getIotDeviceBasicByDeviceCode(deviceCode, iotProjectId);
    }

    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getDeviceListByProjectId(@RequestParam("iotProjectId") Long iotProjectId) {
        return deviceService.getDeviceListByProjectId(iotProjectId);
    }

    @Override
    public ResultDTO<List<IotDeviceQueryDTO>> getDeviceListCustom(@RequestBody IotDeviceListBaseQueryDTO iotDeviceListBaseQueryDTO) {
        return deviceService.getDeviceListCustom(iotDeviceListBaseQueryDTO);
    }

    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getIotDevicesByProjectIdAndProtocolCode(@RequestParam("iotProjectId") Long iotProjectId,
                                                                                       @RequestParam("protocolCode") String protocolCode) {
        return deviceService.getIotDevicesByProjectIdAndProtocolCode(iotProjectId, protocolCode);
    }
}
