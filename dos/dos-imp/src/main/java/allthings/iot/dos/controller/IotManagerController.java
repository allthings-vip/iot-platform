package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotManagerBiz;
import allthings.iot.dos.client.api.IotManagerApi;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/04 17:46:12
 */
@RestController
public class IotManagerController extends IotDosBaseController implements IotManagerApi {

    @Autowired
    private IotManagerBiz managerBiz;

    @Override
    public ResultDTO<Integer> sendMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO) {
        return getResult(managerBiz.sendMessageCode(iotMessageManagerDTO));
    }

    @Override
    public ResultDTO<Integer> sendAppSecretMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO) {
        return getResult(managerBiz.sendAppSecretMessageCode(iotMessageManagerDTO));
    }

}
