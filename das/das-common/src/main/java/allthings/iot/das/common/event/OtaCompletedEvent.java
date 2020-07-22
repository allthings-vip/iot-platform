package allthings.iot.das.common.event;

/**
 * @author :  sylar
 * @FileName :  OtaCompletedEvent
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
public class OtaCompletedEvent extends OtaEvent {
    private String deviceId;

    public OtaCompletedEvent(Object source, String deviceId) {
        super(source);
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
