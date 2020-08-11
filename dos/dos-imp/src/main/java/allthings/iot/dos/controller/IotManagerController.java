package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotMessageService;
import allthings.iot.dos.client.api.IotManagerApi;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/04 17:46:12
 */
@RestController
public class IotManagerController implements IotManagerApi {

    @Autowired
    private IotMessageService managerService;

    @Override
    public ResultDTO<Integer> sendMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO) {
        return managerService.sendMessageCode(iotMessageManagerDTO);
    }

    @Override
    public ResultDTO<Integer> sendAppSecretMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO) {
        return managerService.sendMessageCode(iotMessageManagerDTO);
    }

}
