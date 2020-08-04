package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotManagerApi;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/04 17:46:12
 */
@RestController
public class IotManagerController implements IotManagerApi {
//
//    @Autowired
//    private IotManagerBiz managerBiz;

    @Override
    public ResultDTO<Integer> sendMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO) {
//        return managerBiz.sendMessageCode(iotMessageManagerDTO);
        return null;
    }

    @Override
    public ResultDTO<Integer> sendAppSecretMessageCode(@RequestBody IotMessageManagerDTO iotMessageManagerDTO) {
//        return managerBiz.sendAppSecretMessageCode(iotMessageManagerDTO);
        return null;
    }

}
