package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 14:49
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotDataAggTypeApi {

    @GetMapping("/dos/service/getIotDataAggTypeList")
    ResultDTO<QueryResult<IotDataAggTypeQueryDTO>> getIotDataAggTypeList();
}
