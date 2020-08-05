package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotProtocolDTO;
import allthings.iot.dos.dto.query.IotProtocolDetailDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
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
 * @date 2020/2/17 15:59
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotProtocolApi {

    @GetMapping("/dos/service/getIotProtocolList")
    ResultDTO<List<IotProtocolQueryDTO>> getIotProtocolList();

    @GetMapping("/dos/service/getIotProtocolPageList")
    ResultDTO<QueryResult<IotProtocolDetailDTO>> getIotProtocolList(@RequestParam("keywords") String keywords,
                                                                    @RequestParam("pageIndex") Integer pageIndex,
                                                                    @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/dos/service/saveIotProtocol")
    ResultDTO<Integer> saveIotProtocol(@RequestBody IotProtocolDTO iotProtocolDTO);

    @PostMapping("/dos/service/updateIotProtocol")
    ResultDTO<Integer> updateIotProtocol(@RequestBody IotProtocolDTO iotProtocolDTO);

    @PostMapping("/dos/service/deleteIotProtocol")
    ResultDTO<Integer> deleteIotProtocol(@RequestParam("iotProtocolIds") Long[] iotProtocolIds,
                                         @RequestParam("modifyOperatorId") Long modifyOperatorId);

    @GetMapping("/dos/service/getIotProtocolDetail")
    ResultDTO<IotProtocolDetailDTO> getIotProtocolDetail(@RequestParam("iotProtocolId") Long iotProtocolId);
}
