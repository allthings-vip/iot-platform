package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotEventQueryListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 15:29
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotEventApi {

    @PostMapping("/dos/service/getDeviceEventsByDeviceId")
    ResultDTO<PageResult<IotDeviceEventDTO>> getDeviceEventsByDeviceId(@RequestBody IotEventQueryListDTO iotEventQueryListDTO);

}