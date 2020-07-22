package allthings.iot.common.msg;

/**
 * 设备基本信息消息
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
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class DeviceInfoMsg extends AbstractDeviceMsg {

    /**
     * 设备的业务编码,可由业务层指定
     */
    private String bid;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 固件版本
     */
    private int version;


    @Override
    public MsgType getMsgType() {
        return MsgType.DeviceInfo;
    }


    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
