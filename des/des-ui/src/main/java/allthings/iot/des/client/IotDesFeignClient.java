package allthings.iot.des.client;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.Result;
import allthings.iot.des.dto.IotDesEventTypeDto;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesDeviceEventSaveDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tyf
 * @date 2019/05/15 14:40:51
 */
@FeignClient(name = "des-service", fallback = IotDesFallCallback.class)
public interface IotDesFeignClient {

    /**
     * 保存设备事件
     *
     * @param iotDesDeviceEventSaveDto
     */
    @PostMapping("/des/saveIotDesDeviceEvent")
    void saveIotDesDeviceEvent(@RequestBody IotDesDeviceEventSaveDto iotDesDeviceEventSaveDto);

    /**
     * 根据iotDeviceId查询设备事件列表
     *
     * @param iotDeviceId
     * @param startTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/des/getDeviceEventListByIotDeviceId")
    Result<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventListByIotDeviceId(@RequestParam(name = "iotDeviceId") Long iotDeviceId,
                                                                                      @RequestParam(name = "startTime") Long startTime,
                                                                                      @RequestParam(name = "endTime") Long endTime,
                                                                                      @RequestParam(name = "pageIndex") Integer pageIndex,
                                                                                      @RequestParam(name = "pageSize") Integer pageSize);

    /**
     * 根据设备事件id查询设备事件详情
     *
     * @param iotDesDeviceEventId
     * @return
     */
    @GetMapping("/des/getEventDetailByIotDesDeviceEventId")
    Result<IotDesEventDetailDto> getEventDetailByIotDesDeviceEventId(@RequestParam(name = "iotDesDeviceEventId") Long iotDesDeviceEventId);

    /**
     * 根据事件类型编码查询事件类型信息
     *
     * @param eventTypeCode
     * @return
     */
    @GetMapping("/des/getEventTypeByEventTypeCode")
    Result<IotDesEventTypeDto> getIotEventTypeByEventTypeCode(@RequestParam(name = "eventTypeCode") String eventTypeCode);

}
