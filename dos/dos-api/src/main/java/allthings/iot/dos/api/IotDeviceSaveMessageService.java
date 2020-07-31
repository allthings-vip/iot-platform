package allthings.iot.dos.api;

import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-21 11:16
 */

public interface IotDeviceSaveMessageService {
    /**
     * 发送消息给task
     */
    void sendMessage(List<String> deviceIds, Long iotProjectId);
}
