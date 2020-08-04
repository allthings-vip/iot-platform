package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotProjectService;
import allthings.iot.dos.client.api.IotProjectApi;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.query.IotAppSecretQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeleteQueryDTO;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/02 14:17:21
 */
@RestController
public class IotProjectController implements IotProjectApi {

    @Autowired
    private IotProjectService projectBiz;

    @Override
    public ResultDTO<Long> saveIotProject(@RequestBody IotProjectDTO iotProjectDTO) {
        return projectBiz.saveIotProject(iotProjectDTO);
    }

    @Override
    public ResultDTO<Integer> deleteIotProject(@RequestBody IotProjectDeleteQueryDTO iotProjectDeleteQueryDTO) {
        return projectBiz.deleteIotProject(iotProjectDeleteQueryDTO);
    }

    @Override
    public ResultDTO<Integer> updateIotProject(@RequestBody IotProjectDTO iotProjectDTO) {
        return projectBiz.updateIotProject(iotProjectDTO);
    }

    @Override
    public ResultDTO<PageResult<IotProjectQueryDTO>> getIotProjectList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return projectBiz.getIotProjectList(iotProjectSimpleDTO);
    }

    @Override
    public ResultDTO<List<IotProjectSimpleDTO>> getIotProjectNameList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return projectBiz.getIotProjectNameList(iotProjectSimpleDTO);
    }

    @Override
    public ResultDTO<IotProjectDTO> getIotProjectDetail(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return projectBiz.getIotProjectDetail(iotProjectSimpleDTO);
    }

    @Override
    public ResultDTO<Integer> reviewProject(@RequestBody IotProjectDTO iotProjectDTO) {
        return projectBiz.reviewProject(iotProjectDTO);
    }

    @Override
    public ResultDTO<String> getAppSecret(@RequestBody IotAppSecretQueryDTO iotAppSecretQueryDTO) {
        return projectBiz.getAppSecret(iotAppSecretQueryDTO);
    }

}
