package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotEventPushUrlService;
import allthings.iot.dos.client.api.IotEventPushUrlApi;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/05 09:37:40
 */
@RestController
public class IotEventPushUrlController implements IotEventPushUrlApi {

    @Autowired
    private IotEventPushUrlService eventPushUrlBiz;

    @Override
    public ResultDTO<Long> saveIotEventPushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        return eventPushUrlBiz.saveIotEventPushUrl(iotEventPushUrlDto);
    }

    @Override
    public ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        return eventPushUrlBiz.getEventPushUrlByIotProjectId(iotEventPushUrlDto);
    }

    @Override
    public ResultDTO<Long> updateIotEventPushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        return eventPushUrlBiz.updateIotEventPushUrl(iotEventPushUrlDto);
    }

}
