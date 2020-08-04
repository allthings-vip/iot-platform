package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotEventApi;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotEventQueryListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  luhao
 * @FileName :  IotEventController
 * @CreateDate :  2018-5-12
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
@RestController
public class IotEventController extends BaseController {
    @Autowired
    private IotEventApi iotEventApi;

    /**
     * 获取设备事件
     *
     * @param deviceCode
     * @param startDatetime
     * @param endDatetime
     * @param pageIndex
     * @param pageSize
     * @param disposeStatus
     * @return
     */
    @GetMapping(value = "/events/list")
    public ResultDTO<PageResult<IotDeviceEventDTO>> getEventList(@RequestParam("deviceCode") String deviceCode,
                                                                 @RequestParam("startDatetime") Long startDatetime,
                                                                 @RequestParam("endDatetime") Long endDatetime,
                                                                 @RequestParam("pageIndex") Integer pageIndex,
                                                                 @RequestParam("pageSize") Integer pageSize,
                                                                 @RequestParam(value = "disposeStatus", required =
                                                                         false) String disposeStatus,
                                                                 @RequestParam("iotProjectId") Long iotProjectId) {

        IotEventQueryListDTO iotEventQueryListDTO = new IotEventQueryListDTO();
        iotEventQueryListDTO.setDeviceCode(deviceCode);
        iotEventQueryListDTO.setDisposeStatus(disposeStatus);
        iotEventQueryListDTO.setStartDatetime(startDatetime);
        iotEventQueryListDTO.setEndDatetime(endDatetime);
        iotEventQueryListDTO.setIotProjectId(iotProjectId);
        iotEventQueryListDTO.setPageIndex(pageIndex);
        iotEventQueryListDTO.setPageSize(pageSize);

        return iotEventApi.getDeviceEventsByDeviceId(iotEventQueryListDTO);
    }

    @PostMapping("/events/update")
    public ResultDTO<?> updateEvent(@RequestParam("description") String description,
                                    @RequestParam("iotDeviceEventId") Long iotDeviceEventId,
                                    @RequestParam("iotProjectId") Long iotProjectId) {
        return ResultDTO.newSuccess();
    }


}
