package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotFactorRangeValueQueryDTO;
import allthings.iot.dos.dto.query.IotFactorValueQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 15:51
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotKvDataApi {

    @GetMapping("/dos/service/getKVLatest")
    ResultDTO<List<IotFactorValueQueryDTO>> getKVLatest(@RequestParam("deviceCode") String deviceCode,
                                                        @RequestParam("partyId") String partyId,
                                                        @RequestParam("iotProjectId") Long iotProjectId,
                                                        @RequestParam("userId") Long userId,
                                                        @RequestParam("roleCode") String roleCode);

    @GetMapping("/dos/service/getKVRange")
    ResultDTO<List<IotFactorRangeValueQueryDTO>> getKVRange(@RequestParam("deviceCode") String deviceCode,
                                                            @RequestParam("partyId") String partyId,
                                                            @RequestParam("factorCodes") String[] factorCodes,
                                                            @RequestParam("startDatetime") Long startDatetime,
                                                            @RequestParam("endDatetime") Long endDatetime,
                                                            @RequestParam("iotProjectId") Long iotProjectId,
                                                            @RequestParam("userId") Long userId,
                                                            @RequestParam("roleCode") String roleCode);
}
