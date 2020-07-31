package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotLoggerTypeDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;
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
 * @date 2020/2/17 15:53
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotLoggerApi {
    @PostMapping("/dos/service/saveIotDeviceLogger")
    ResultDTO<Object> saveIotDeviceLogger(@RequestBody IotLogDTO logDTO);

    @PostMapping("/dos/service/getDeviceLoggerList")
    ResultDTO<PageResult<IotDeviceLoggerListDto>> getDeviceLoggerList(@RequestBody IotDeviceLoggerQueryListDto iotDeviceLoggerQueryListDto);

    @PostMapping("/dos/service/getSystemLoggerList")
    ResultDTO<PageResult<IotDeviceLoggerListDto>> getSystemLoggerList(@RequestBody IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto);

    @GetMapping("/dos/service/getLoggerTypeIdByLoggerTypeCode")
    ResultDTO<Long> getLoggerTypeIdByLoggerTypeCode(@RequestParam("loggerTypeCode") String loggerTypeCode);

    @GetMapping("/dos/service/getSystemLoggerType")
    ResultDTO<List<IotLoggerTypeDto>> getSystemLoggerType();
}
