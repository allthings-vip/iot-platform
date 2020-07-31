package allthings.iot.dos.api;


import allthings.iot.common.msg.IMsg;

/**
 * @author tyf
 * @date 2019/03/18 14:37
 */
public interface IotConsumerService {


    /**
     * 保存设备日志
     *
     * @param msg
     */
    void saveDeviceLogger(IMsg msg);

    /**
     * 保存设备通道
     *
     * @param msg
     * @return
     */
    void saveDevicePass(IMsg msg);
}
