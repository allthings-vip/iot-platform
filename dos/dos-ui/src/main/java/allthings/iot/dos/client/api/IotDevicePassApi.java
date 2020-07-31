package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotDevicePassQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 14:55
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotDevicePassApi {

    @PostMapping("/dos/service/saveDevicePass")
    ResultDTO<Integer> saveDevicePass(@RequestBody IotDevicePassDto devicePassDto);

    @PostMapping("/dos/service/getDevicePassList")
    ResultDTO<List<IotDevicePassDto>> getDevicePassList(@RequestBody IotDeviceDTO deviceDTO);

    @GetMapping("/dos/service/getDevicePassDetail")
    ResultDTO<?> getDevicePassDetail(@RequestParam("iotDosDevicePassId") Long iotDosDevicePassId);

    @PostMapping("/dos/service/getPassLiveStream")
    ResultDTO<IotVisResultDTO> getPassLiveStream(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDto);

    @PostMapping("/dos/service/getPassPlayBack")
    ResultDTO<IotVisResultDTO> getPassPlayBack(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDto);

    @PostMapping("/dos/service/controlDevicePass")
    ResultDTO<Integer> controlDevicePass(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDTO);

    @PostMapping("/dos/service/stopControlDevicePass")
    ResultDTO<Integer> stopControlDevicePass(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDTO);
    
    @PostMapping("/dos/service/getIotDevicePassListByIotPassTypeId")
    ResultDTO<List<IotDevicePassDto>> getIotDevicePassListByIotPassTypeId(@RequestParam("iotDeviceId") Long iotDeviceId,
                                                                          @RequestParam(value = "iotPassTypeId",required = false)
                                                                                  Long iotPassTypeId);

}
