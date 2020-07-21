package allthings.iot.common.msg;

import allthings.iot.common.pojo.DeviceGuid;

/**
 * 设备实时数据消息
 */

/**
 * @author :  sylar
 * @FileName :  DeviceDataMsg
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class DeviceDataMsg extends AbstractDeviceMsg {

    private long timestamp = System.currentTimeMillis();

    @Override
    public MsgType getMsgType() {
        return MsgType.DeviceData;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static DeviceDataMsg newMsgFromCloud() {
        DeviceDataMsg msg = new DeviceDataMsg();
        msg.setSourceDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());
        return msg;
    }

    public static DeviceDataMsg newMsgToCloud(String msgCode) {
        DeviceDataMsg msg = newMsgToCloud();
        msg.setMsgCode(msgCode);
        return msg;
    }

    public static DeviceDataMsg newMsgToCloud() {
        DeviceDataMsg msg = new DeviceDataMsg();
        msg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());
        return msg;
    }
}
