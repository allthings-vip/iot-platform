package allthings.iot.dos.client.open;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.open.IotDeviceListQueryDTO;
import allthings.iot.dos.dto.open.IotDeviceOpenDTO;
import allthings.iot.dos.dto.open.IotDeviceSaveBatchDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeDeleteByCodeDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeSaveDTO;
import allthings.iot.dos.dto.open.IotEventQueryDTO;
import allthings.iot.dos.dto.open.IotLogQueryDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceTypeListQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 17:36
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotDeviceOpenApi {
    
    @PostMapping("/dos/service/getEventList")
    ResultDTO<PageResult<IotDeviceEventDTO>> getEventList(@RequestBody IotEventQueryDTO iotEventQueryDTO);

    @PostMapping("/dos/service/getFactorListByDevcieTypeCode")
    ResultDTO<List<IotFactorQueryDTO>> getFactorListByDeviceTypeCode(@RequestParam("deviceTypeCode") String deviceTypeCode,
                                                                     @RequestParam("iotProjectId") Long iotProjectId);


    @PostMapping("/dos/service/getLogList")
    ResultDTO<PageResult<IotLogDTO>> getLogList(@RequestBody IotLogQueryDTO iotLogQueryDTO);

    @GetMapping("/dos/service/updateDeviceStatus")
    ResultDTO<Integer> updateDeviceStatus(@RequestParam("deviceCodes") String[] deviceCodes,
                                          @RequestParam("status") Integer status,
                                          @RequestParam("iotProjectId") Long iotProjectId);

    @GetMapping("/dos/service/getDeviceStatusBatch")
    ResultDTO<List<IotDeviceStatusQueryDTO>> getDeviceStatusBatch(@RequestParam("deviceCodes") String[] deviceCodes,
                                                                  @RequestParam("iotProjectId") Long iotProjectId);

    @PostMapping("/dos/service/saveOrUpdateDevice")
    ResultDTO<Integer> saveOrUpdateDevice(@RequestBody IotDeviceOpenDTO iotDeviceOpenDTO);

    @PostMapping("/dos/service/saveOrUpdateDeviceOpenApi")
    ResultDTO<IotOpenApiResponseDeviceDTO> saveOrUpdateDeviceOpenApi(@RequestBody IotDeviceOpenDTO iotDeviceOpenDTO);

    @PostMapping("/dos/service/saveOrUpdateDeviceBatch")
    ResultDTO<List<IotOpenApiResponseDeviceDTO>> saveOrUpdateDeviceBatch(@RequestBody IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO);

    @PostMapping("/dos/service/getDeviceList")
    ResultDTO<PageResult<IotOpenDeviceDTO>> getDeviceList(@RequestBody IotDeviceListQueryDTO iotDeviceListQueryDTO);

    @PostMapping("/dos/service/deleteDeviceType")
    ResultDTO<Integer> deleteDeviceType(@RequestBody IotDeviceTypeDeleteByCodeDTO iotDeviceTypeDeleteByCodeDTO);

    @PostMapping("/dos/service/saveOrUpdateDeviceType")
    ResultDTO<Integer> saveOrUpdateDeviceType(@RequestBody IotDeviceTypeSaveDTO iotDeviceTypeSaveDTO);

    @PostMapping("/dos/service/getDeviceTypeList")
    ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> getDeviceTypeList(@RequestBody IotOpenDeviceTypeListQueryDTO iotOpenDeviceTypeListQueryDTO);

}
