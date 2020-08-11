package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;

/**
 * @author tyf
 * @date 2020/08/11 09:30:28
 */
public interface IotMessageService {
    /**
     * 发送注册验证码
     *
     * @param iotMessageManagerDTO
     * @return
     */
    ResultDTO<Integer> sendMessageCode(IotMessageManagerDTO iotMessageManagerDTO);

    /**
     * 验证码校验
     *
     * @param iotMessageManagerDTO
     * @return
     */
    ResultDTO<Integer> validateIdentifyCode(IotMessageManagerDTO iotMessageManagerDTO);
}
