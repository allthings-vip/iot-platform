package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceTagDTO;
import allthings.iot.dos.dto.IotTagDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 16:00
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotTagApi {

    @PostMapping("/dos/service/getIotTagList")
    ResultDTO<List<IotTagQueryDTO>> getIotTagList(@RequestBody IotTagDTO iotTagDTO);

    @PostMapping("/dos/service/getDeviceByIotTagIdAndIotProjectId")
    ResultDTO<List<IotDeviceDTO>> getDeviceByIotTagIdAndIotProjectId(@RequestBody IotTagQueryDTO iotTagQueryDTO,
                                                                     @RequestParam("choose") Boolean choose);

    @PostMapping("/dos/service/deleteTagByTagId")
    ResultDTO<Integer> deleteTagByTagId(@RequestBody IotTagDTO iotTagDTO);

    @PostMapping("/dos/service/saveTag")
    ResultDTO<Integer> saveTag(@RequestBody IotTagDTO iotTagDTO);

    @PostMapping("/dos/service/saveDeviceOfTag")
    ResultDTO<Integer> saveDeviceOfTag(@RequestBody IotDeviceTagDTO iotDeviceTagDTO);

    @PostMapping("/dos/service/deleteDeviceOfTag")
    ResultDTO<Integer> deleteDeviceOfTag(@RequestBody IotDeviceTagDTO iotDeviceTagDTO);
}
