package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotFactorDTO;
import allthings.iot.dos.dto.query.IotFactorQuerListDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 15:47
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotFactorApi {
    @PostMapping("/dos/service/saveIotFactor")
    ResultDTO<Long> saveIotFactor(@RequestBody IotFactorDTO iotFactorDTO);

    @PostMapping("/dos/service/updateIotFactor")
    ResultDTO<Long> updateIotFactor(@RequestBody IotFactorDTO iotFactorDTO);

    @PostMapping("/dos/service/deleteIotFactor")
    ResultDTO<Integer> deleteIotFactor(@RequestParam("iotFactorIds") Long[] iotFactorIds,
                                       @RequestParam("operator") String operator);

    @PostMapping("/dos/service/getIotFactorList")
    ResultDTO<List<IotFactorQueryDTO>> getIotFactorList(@RequestBody IotFactorQuerListDTO iotFactorQuerListDTO);

    @GetMapping("/dos/service/getIotFactorDetail")
    ResultDTO<IotFactorQueryDTO> getIotFactorDetail(@RequestParam("iotFactorId") Long iotFactorId, @RequestParam(
            "operator") String operator);
}
