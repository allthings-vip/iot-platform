package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountTitleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 15:57
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotPointCountApi {

    @PostMapping("/dos/service/getByDateRange")
    ResultDTO<List<IotPointCountQueryDTO>> getByDateRange(@RequestBody IotDosQueryDTO iotDosQueryDTO);

    @GetMapping("/dos/service/getTopByDateRange")
    ResultDTO<QueryResult<IotPointCountTitleDTO>> getTopByDateRange(@RequestParam("startDatetime") Long startDatetime,
                                                                    @RequestParam("endDatetime") Long endDatetime,
                                                                    @RequestParam("type") String type,
                                                                    @RequestParam("top") Integer top);
}
