package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotDosOverviewDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 15:57
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotOverviewApi {
    @PostMapping("/dos/service/overview")
    ResultDTO<IotDosOverviewDTO> overview(@RequestBody IotUserQueryDTO iotUserQueryDTO);

    @PostMapping("/dos/service/overviewByIotProjectId")
    ResultDTO<IotDosOverviewDTO> overviewByIotProjectId(@RequestBody IotUserQueryDTO iotUserQueryDTO);
}
