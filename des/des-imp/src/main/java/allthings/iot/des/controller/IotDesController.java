package allthings.iot.des.controller;

import allthings.iot.des.dto.IotDesEventTypeDto;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesDeviceEventSaveDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.des.api.IotDesDeviceEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.Result;

/**
 * @author tyf
 * @date 2019/05/14 16:32:43
 */
@RestController
public class IotDesController {

    @Autowired
    private IotDesDeviceEventService desDeviceEventService;

    /**
     * 保存设备事件
     *
     * @param iotDesDeviceEventSaveDto
     */
    @PostMapping("/des/saveIotDesDeviceEvent")
    public void saveIotDesDeviceEvent(@RequestBody IotDesDeviceEventSaveDto iotDesDeviceEventSaveDto) {
        desDeviceEventService.saveIotDesDeviceEvent(iotDesDeviceEventSaveDto);
    }

    @GetMapping("/des/getDeviceEventListByIotDeviceId")
    public Result<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventListByIotDeviceId(@RequestParam(name = "iotDeviceId") Long iotDeviceId,
                                                                                             @RequestParam(name = "startTime") Long startTime,
                                                                                             @RequestParam(name = "endTime") Long endTime,
                                                                                             @RequestParam(name = "pageIndex") Integer pageIndex,
                                                                                             @RequestParam(name = "pageSize") Integer pageSize) {
        return desDeviceEventService.getDeviceEventListByIotDeviceId(iotDeviceId, startTime, endTime, pageIndex, pageSize);
    }

    @GetMapping("/des/getEventDetailByIotDesDeviceEventId")
    public Result<IotDesEventDetailDto> getEventDetailByIotDesDeviceEventId(@RequestParam(name = "iotDesDeviceEventId") Long iotDesDeviceEventId) {
        return desDeviceEventService.getEventDetailByIotDesDeviceEventId(iotDesDeviceEventId);
    }

    /**
     * 根据事件类型编码查询事件类型信息
     *
     * @param eventTypeCode
     * @return
     */
    @GetMapping("/des/getEventTypeByEventTypeCode")
    public Result<IotDesEventTypeDto> getEventTypeByEventTypeCode(@RequestParam(name = "eventTypeCode") String eventTypeCode) {
        return desDeviceEventService.getEventTypeByEventTypeCode(eventTypeCode);
    }

}
