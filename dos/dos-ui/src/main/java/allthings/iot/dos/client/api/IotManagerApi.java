package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 15:56
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotManagerApi {

    @PostMapping("/dos/service/sendMessageCode")
    ResultDTO<Integer> sendMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO);

    @PostMapping("/dos/service/sendAppSecretMessageCode")
    ResultDTO<Integer> sendAppSecretMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO);

}
