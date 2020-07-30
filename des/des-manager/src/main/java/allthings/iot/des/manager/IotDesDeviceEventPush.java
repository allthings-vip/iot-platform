package allthings.iot.des.manager;

import allthings.iot.common.msg.DeviceEventMsg;

/**
 * @author tyf
 * @date 2019/03/07 10:40
 */
public interface IotDesDeviceEventPush {

    /**
     * 推送事件信息
     *
     * @param url
     * @param msg
     */
    void publishDeviceEvent(String url, DeviceEventMsg msg) throws Exception;

}
