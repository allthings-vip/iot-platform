package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotEventService;
import allthings.iot.dos.client.api.IotEventApi;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotEventQueryListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/04 14:30:14
 */
@RestController
public class IotEventController implements IotEventApi {

    @Autowired
    private IotEventService eventBiz;

    @Override
    public ResultDTO<PageResult<IotDeviceEventDTO>> getDeviceEventsByDeviceId(@RequestBody IotEventQueryListDTO iotEventQueryListDTO) {
        return eventBiz.getDeviceEventsByDeviceId(iotEventQueryListDTO);
    }

}
