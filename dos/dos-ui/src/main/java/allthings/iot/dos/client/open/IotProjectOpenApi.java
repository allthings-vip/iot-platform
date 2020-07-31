package allthings.iot.dos.client.open;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 17:55
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotProjectOpenApi {

    @GetMapping("/dos/service/getIotProject")
    ResultDTO<Long> getIotProject(@RequestParam("clientId") String clientId);

    @GetMapping("/dos/service/hasDevice")
    ResultDTO<Long> hasDevice(@RequestParam("clientId") String clientId,
                              @RequestParam("deviceCodes") String[] deviceCodes,
                              @RequestParam("enabled") Boolean enabled);

    @GetMapping("/dos/service/hasDeviceType")
    ResultDTO<Long> hasDeviceType(@RequestParam("clientId") String clientId,
                                  @RequestParam("deviceTypeCodes") String[] deviceTypeCodes);



}
