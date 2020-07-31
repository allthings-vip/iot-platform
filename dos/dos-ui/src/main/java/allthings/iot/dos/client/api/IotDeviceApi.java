package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;
import java.util.Map;

/**
 * @author luhao
 * @date 2020/2/17 12:22
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotDeviceApi {

    @PostMapping("/dos/service/saveIotDevice")
    ResultDTO<Long> saveIotDevice(@RequestBody IotDeviceDTO iotDeviceDTO);

    @PostMapping("/dos/service/saveIotDeviceBatch")
    ResultDTO<List<IotDeviceErrorMsgDTO>> saveIotDevice(@RequestBody IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO);

    @PostMapping("/dos/service/updateIotDevice")
    ResultDTO<Long> updateIotDevice(@RequestBody IotDeviceDTO iotDeviceDTO);

    @PostMapping("/dos/service/updateIotDeviceStatus")
    ResultDTO<Integer> updateIotDeviceStatus(@RequestBody IotDeviceStatusDTO iotDeviceStatusDTO);

    @PostMapping("/dos/service/getDeviceCountByOnlineStatus")
    ResultDTO<IotDeviceStatusCountDTO> getDeviceCountByOnlineStatus(@RequestBody IotDeviceQueryListDTO iotDeviceQueryListDTO);

    @PostMapping("/dos/service/getDeviceCountByType")
    ResultDTO<List<IotDeviceCountQueryDTO>> getDeviceCountByType(@RequestBody IotDosQueryDTO iotDosQueryDTO);

    @GetMapping("/dos/service/getDeviceCountTopByType")
    ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountTopByType(@RequestParam("startDatetime") Long startDatetime,
                                                                           @RequestParam("endDatetime") Long endDatetime,
                                                                           @RequestParam("top") Integer top,
                                                                           @RequestParam("type") String type);

    @PostMapping("/dos/service/getIotDeviceDetailByDeviceId")
    ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(@RequestBody IotDeviceDTO iotDeviceDTO);

    @PostMapping("/dos/service/getIotDeviceByIotProjectId")
    ResultDTO<PageResult<IotDeviceQueryDTO>> getIotDeviceByIotProjectId(@RequestBody IotDeviceQueryListDTO iotDeviceQueryListDTO);

    @PostMapping("/dos/service/getIotDeviceDetailByDeviceCode")
    ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(@RequestBody IotDeviceQueryByCodeDTO iotDeviceQueryByCodeDTO);

    @GetMapping("/dos/service/getDeviceCountByTag")
    ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountByTag(@RequestParam("iotTagId") Long iotTagId,
                                                                       @RequestParam("startDatetime") Long startDatetime,
                                                                       @RequestParam("endDatetime") Long endDatetime);

    @PostMapping("/dos/service/getIotDeviceStatus")
    ResultDTO<List<IotDeviceStatusQueryDTO>> getIotDeviceStatus(@RequestBody IotDeviceStatusBatchQueryDTO iotDeviceStatusBatchQueryDTO);


    @PostMapping("/dos/service/getProtocolByDeviceCode")
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(@RequestBody IotDeviceDTO iotDeviceDTO);

    @PostMapping("/dos/service/getProtocolByDeviceCodes")
    ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(@RequestBody IotDeviceStatusBatchQueryDTO batchQueryDTO);

    @PostMapping("/dos/service/getIotDeviceByRegister")
    ResultDTO<PageResult<IotDeviceDTO>> getIotDeviceByRegister(@RequestBody IotDeviceMonitorQueryListDTO iotDeviceMonitorQueryListDTO);

    @PostMapping("/dos/service/updateIotDeviceRegister")
    ResultDTO<Long> updateIotDeviceRegister(@RequestBody IotDeviceDTO iotDeviceDTO);

    @PostMapping("/dos/service/updateIotDeviceRegisterBatch")
    ResultDTO<Integer> updateIotDeviceRegisterBatch(@RequestBody IotDeviceRegisterBatchDTO iotDeviceRegisterBatchDTO);


    @PostMapping("/dos/service/getDeviceLocation")
    ResultDTO<List<IotDeviceQueryDTO>> getDeviceLocation(@RequestBody IotDeviceLocationQueryDTO iotDeviceLocationQueryDTO);


    @PostMapping("/dos/service/history")
    ResultDTO<List<Map<String, String>>> history(@RequestBody IotDeviceHistoryQueryDTO iotDeviceHistoryQueryDTO);

    @PostMapping("/dos/service/getDeviceIdByDeviceCode")
    ResultDTO<String> getDeviceIdByDeviceCode(@RequestBody IotDeviceDTO iotDeviceDTO);

    @GetMapping("/dos/service/getDeviceIdByDeviceCode")
    ResultDTO<String> getDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    @GetMapping("/dos/service/getIotProjectIdByDeviceId")
    ResultDTO<List<Long>> getIotProjectIdByDeviceId(@RequestParam("deviceId") String deviceId);

    @GetMapping("/dos/service/getIotProjectIdByDeviceCode")
    ResultDTO<List<Long>> getIotProjectIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    @GetMapping("/dos/service/getSimpleDevices")
    ResultDTO<List<IotDeviceSimpleDTO>> getSimpleDevices();

    @GetMapping("/dos/service/getIotDeviceIdByDeviceCode")
    ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    @PostMapping("/dos/service/getDeviceEventList")
    ResultDTO<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventList(@RequestBody IotDosDeviceEventQueryListDto deviceDTO);

    @PostMapping("/dos/service/getDeviceEventDetail")
    ResultDTO<IotDesEventDetailDto> getDeviceEventDetail(@RequestBody IotDeviceEventDetailQueryDto deviceEventDetailQueryDto);

    @PostMapping("/dos/service/getDeviceLoggerListByIotDeviceId")
    ResultDTO getDeviceLoggerListByIotDeviceId(@RequestBody IotDeviceLoggerQueryListDto deviceLoggerQueryListDto);

    @GetMapping("/dos/service/getDeviceIdByBizCode")
    ResultDTO<String> getDeviceIdByBizCode(@RequestParam("bizCode") String bizCode);

    @GetMapping("/dos/service/getProtocolByBizCode")
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(@RequestParam("bizCode") String bizCode);

    @GetMapping("/dos/service/getIotDeviceBasicByDeviceCode")
    ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(@RequestParam("deviceCode") String deviceCode, @RequestParam(
            "iotProjectId") Long iotProjectId);

    @GetMapping("/dos/service/getDeviceListByProjectId")
    ResultDTO<List<IotDeviceDetailDTO>> getDeviceListByProjectId(@RequestParam("iotProjectId") Long iotProjectId);

    @PostMapping("/dos/service/device/list/custom")
    ResultDTO<List<IotDeviceQueryDTO>> getDeviceListCustom(@RequestBody IotDeviceListBaseQueryDTO iotDeviceListBaseQueryDTO);

    @GetMapping("/dos/service/device/list/protocol")
    ResultDTO<List<IotDeviceDetailDTO>> getIotDevicesByProjectIdAndProtocolCode(@RequestParam("iotProjectId") Long iotProjectId,
                                                                                @RequestParam("protocolCode") String protocolCode);
}
