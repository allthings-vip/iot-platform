package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotManagerApi;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-09 16:21
 */
@RestController
public class IotMessageController extends BaseController {
    @Autowired
    private IotManagerApi iotManagerApi;

    @GetMapping("/code/get")
    public ResultDTO<Integer> getCode(@RequestParam("mobile") String mobile) {
        IotMessageManagerDTO iotMessageManagerDTO = new IotMessageManagerDTO();
        iotMessageManagerDTO.setMobileNumber(mobile);
        return iotManagerApi.sendMessageCode(iotMessageManagerDTO);
    }

    @GetMapping("/code/appsecret/get")
    public ResultDTO<Integer> getAppSecretCode() {
        IotMessageManagerDTO iotMessageManagerDTO = new IotMessageManagerDTO();
        iotMessageManagerDTO.setMobileNumber(getUser().getMobile());
        return iotManagerApi.sendAppSecretMessageCode(iotMessageManagerDTO);
    }
}
