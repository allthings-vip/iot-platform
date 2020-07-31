package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotEventBiz;
import allthings.iot.dos.client.api.IotEventApi;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotEventQueryListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.PageResult;
import tf56.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/04 14:30:14
 */
@RestController
public class IotEventController extends IotDosBaseController implements IotEventApi {

    @Autowired
    private IotEventBiz eventBiz;

    @Override
    public ResultDTO<PageResult<IotDeviceEventDTO>> getDeviceEventsByDeviceId(@RequestBody IotEventQueryListDTO iotEventQueryListDTO) {
        return getResult(eventBiz.getDeviceEventsByDeviceId(iotEventQueryListDTO));
    }

}
