package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotProjectBiz;
import allthings.iot.dos.client.api.IotProjectApi;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.query.IotAppSecretQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeleteQueryDTO;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.PageResult;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/02 14:17:21
 */
@RestController
public class IotProjectController extends IotDosBaseController implements IotProjectApi {

    @Autowired
    private IotProjectBiz projectBiz;

    @Override
    public ResultDTO<Long> saveIotProject(@RequestBody IotProjectDTO iotProjectDTO) {
        return getResult(projectBiz.saveIotProject(iotProjectDTO));
    }

    @Override
    public ResultDTO<Integer> deleteIotProject(@RequestBody IotProjectDeleteQueryDTO iotProjectDeleteQueryDTO) {
        return getResult(projectBiz.deleteIotProject(iotProjectDeleteQueryDTO));
    }

    @Override
    public ResultDTO<Integer> updateIotProject(@RequestBody IotProjectDTO iotProjectDTO) {
        return getResult(projectBiz.updateIotProject(iotProjectDTO));
    }

    @Override
    public ResultDTO<PageResult<IotProjectQueryDTO>> getIotProjectList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return getResult(projectBiz.getIotProjectList(iotProjectSimpleDTO));
    }

    @Override
    public ResultDTO<List<IotProjectSimpleDTO>> getIotProjectNameList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return getResult(projectBiz.getIotProjectNameList(iotProjectSimpleDTO));
    }

    @Override
    public ResultDTO<IotProjectDTO> getIotProjectDetail(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return getResult(projectBiz.getIotProjectDetail(iotProjectSimpleDTO));
    }

    @Override
    public ResultDTO<Integer> reviewProject(@RequestBody IotProjectDTO iotProjectDTO) {
        return getResult(projectBiz.reviewProject(iotProjectDTO));
    }

    @Override
    public ResultDTO<String> getAppSecret(@RequestBody IotAppSecretQueryDTO iotAppSecretQueryDTO) {
        return getResult(projectBiz.getAppSecret(iotAppSecretQueryDTO));
    }

}
