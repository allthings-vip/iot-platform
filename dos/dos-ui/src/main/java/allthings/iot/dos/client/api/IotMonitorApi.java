package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotExternalDevicePlatformDTO;
import allthings.iot.dos.dto.IotServiceDTO;
import allthings.iot.dos.dto.IotServiceListDto;
import allthings.iot.dos.dto.query.IotMonitorQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 16:04
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotMonitorApi {
    @PostMapping("/dos/service/saveService")
    ResultDTO<Integer> saveService(@RequestBody IotServiceDTO iotServiceDTO);

    @GetMapping("/dos/service/getIotServiceByIPAndPort")
    ResultDTO<IotServiceDTO> getIotServiceByIPAndPort(@RequestParam("ip") String ip, @RequestParam("port") String port);

    @PostMapping("/dos/service/getIotServiceLists")
    ResultDTO<PageResult<IotServiceDTO>> getIotServiceLists(@RequestBody IotMonitorQueryDTO iotMonitorQueryDTO);

    @GetMapping("/dos/service/getServiceInfoTopology")
    ResultDTO<PageResult<IotServiceDTO>> getServiceInfoTopology();

    @PostMapping("/dos/service/updateService")
    ResultDTO<Integer> updateService(@RequestBody IotServiceDTO iotServiceDTO);

    @PostMapping("/dos/service/deleteService")
    ResultDTO<Integer> deleteService(@RequestParam("iotServiceId") Long iotServiceId);

    @GetMapping("/dos/service/getPlatFormList")
    ResultDTO<List<IotExternalDevicePlatformDTO>> getPlatFormList();

    @GetMapping("/dos/service/getPlatFormByCode")
    ResultDTO<IotExternalDevicePlatformDTO> getPlatFormByCode(@RequestParam("code") String code);

    @PostMapping("/dos/service/saveAll")
    ResultDTO<List<IotServiceDTO>> saveAll(@RequestBody IotServiceListDto services);

}
