package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.query.IotAppSecretQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeleteQueryDTO;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 15:58
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotProjectApi {
    @PostMapping("/dos/service/saveIotProject")
    ResultDTO<Long> saveIotProject(@RequestBody IotProjectDTO iotProjectDTO);

    @PostMapping("/dos/service/deleteIotProject")
    ResultDTO<Integer> deleteIotProject(@RequestBody IotProjectDeleteQueryDTO iotProjectDeleteQueryDTO);

    @PostMapping("/dos/service/updateIotProject")
    ResultDTO<Integer> updateIotProject(@RequestBody IotProjectDTO iotProjectDTO);

    @PostMapping("/dos/service/getIotProjectList")
    ResultDTO<PageResult<IotProjectQueryDTO>> getIotProjectList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO);

    @PostMapping("/dos/service/getIotProjectNameList")
    ResultDTO<List<IotProjectSimpleDTO>> getIotProjectNameList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO);

    @PostMapping("/dos/service/getIotProjectDetail")
    ResultDTO<IotProjectDTO> getIotProjectDetail(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO);

    @PostMapping("/dos/service/reviewProject")
    ResultDTO<Integer> reviewProject(@RequestBody IotProjectDTO iotProjectDTO);

    @PostMapping("/dos/service/getAppSecret")
    ResultDTO<String> getAppSecret(@RequestBody IotAppSecretQueryDTO iotAppSecretQueryDTO);

}
