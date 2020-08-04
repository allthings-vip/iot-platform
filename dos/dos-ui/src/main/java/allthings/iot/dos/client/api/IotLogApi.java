package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 15:52
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotLogApi {

    @GetMapping("/dos/service/getMsgLogs")
    ResultDTO<PageResult<IotLogDTO>> getMsgLogs(@RequestParam("deviceCode") String deviceCode,
                                                @RequestParam("msgType") String msgType,
                                                @RequestParam("beginDatetime") long beginDatetime,
                                                @RequestParam("endDatetime") long endDatetime,
                                                @RequestParam("pageIndex") int pageIndex,
                                                @RequestParam("pageSize") int pageSize,
                                                @RequestParam("iotProjectId") Long iotProjectId,
                                                @RequestParam("userId") Long userId,
                                                @RequestParam("roleCode") String roleCode);
}
