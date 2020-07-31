package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotPassTypeHystrix;
import allthings.iot.dos.dto.IotPassTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 11:55
 */
@FeignClient(name = "${service.name}", fallback = IotPassTypeHystrix.class)
public interface IotPassTypeApi {

    @GetMapping("/passtype/getAllByIotDeviceType")
    ResultDTO<List<IotPassTypeDTO>> getAllByIotDeviceType(@RequestParam("iotDeviceTypeId") Long iotDeviceTypeId);

}
