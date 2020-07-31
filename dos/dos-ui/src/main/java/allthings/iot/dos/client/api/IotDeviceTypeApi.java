package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
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
 * @date 2020/2/17 15:23
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotDeviceTypeApi {

    @GetMapping("/dos/service/judgeProject")
    ResultDTO<Integer> judgeProject(@RequestParam("iotProjectId") Long iotProjectId,
                                    @RequestParam("userId") Long userId,
                                    @RequestParam("roleCode") String roleCode);

    @PostMapping("/dos/service/updateIotDeviceType")
    ResultDTO<Integer> updateIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO);

    @PostMapping("/dos/service/saveIotDeviceType")
    ResultDTO<Long> saveIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO);

    @PostMapping("/dos/service/updateIotDeviceTypeStatus")
    ResultDTO<Integer> updateIotDeviceTypeStatus(@RequestParam("iotDeviceTypeIds") Long[] iotDeviceTypeIds,
                                                 @RequestParam("operator") String operator,
                                                 @RequestParam("isEnabled") Integer isEnabled);

    @PostMapping("/dos/service/deleteIotDeviceType")
    ResultDTO<Integer> deleteIotDeviceType(@RequestBody IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO);

    @PostMapping("/dos/service/dso/service/getIotDeviceTypeList")
    ResultDTO<PageResult<IotDeviceTypeQueryDTO>> getIotDeviceTypeList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO);

    @PostMapping("/dos/service/getIotDeviceTypeDetail")
    ResultDTO<IotDeviceTypeDTO> getIotDeviceTypeDetail(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO);

    @PostMapping("/dos/service/getIotDeviceTypeByIotProjectId")
    ResultDTO<List<IotProjectDeviceTypeDTO>> getIotDeviceTypeByIotProjectId(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO);
}
