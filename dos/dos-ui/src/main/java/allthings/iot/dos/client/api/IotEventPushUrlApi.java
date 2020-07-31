package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 15:40
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotEventPushUrlApi {

    @PostMapping("/dos/service/saveIotEventPushUrl")
    ResultDTO<Long> saveIotEventPushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto);

    @PostMapping("/dos/service/getEventPushUrlByIotProjectId")
    ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(@RequestBody IotEventPushUrlDto iotEventPushUrlDto);

    @PostMapping("/dos/service/updateIotEventPushUrl")
    ResultDTO<Long> updateIotEventPushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto);

}
