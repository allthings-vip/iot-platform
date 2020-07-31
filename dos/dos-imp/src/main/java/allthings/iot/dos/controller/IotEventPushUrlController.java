package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotEventPushUrlBiz;
import allthings.iot.dos.client.api.IotEventPushUrlApi;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/05 09:37:40
 */
@RestController
public class IotEventPushUrlController extends IotDosBaseController implements IotEventPushUrlApi {

    @Autowired
    private IotEventPushUrlBiz eventPushUrlBiz;

    @Override
    public ResultDTO<Long> saveIotEventPushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        return getResult(eventPushUrlBiz.saveIotEventPushUrl(iotEventPushUrlDto));
    }

    @Override
    public ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        return getResult(eventPushUrlBiz.getEventPushUrlByIotProjectId(iotEventPushUrlDto));
    }

    @Override
    public ResultDTO<Long> updateIotEventPushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        return getResult(eventPushUrlBiz.updateIotEventPushUrl(iotEventPushUrlDto));
    }

}
