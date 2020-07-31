package allthings.iot.dos.client.open;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotUseLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 18:54
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotUseLogApi {
    /**
     * 保存接口调用记录
     *
     * @param iotUseLogDTO
     * @return
     */
    @PostMapping("/dos/service/use/log/save")
    ResultDTO saveUseLog(@RequestBody IotUseLogDTO iotUseLogDTO);
}
