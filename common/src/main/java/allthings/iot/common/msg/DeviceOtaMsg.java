package allthings.iot.common.msg;

/**
 * 设备ota升级消息
 * 设备ota是由云端发起,并作分包处理后发给设备的
 */

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class DeviceOtaMsg extends AbstractDeviceMsg {

    private byte[] otaData;


    @Override
    public MsgType getMsgType() {
        return MsgType.DeviceOta;
    }

    public byte[] getOtaData() {
        return otaData;
    }

    public void setOtaData(byte[] otaData) {
        this.otaData = otaData;
    }
}
