package allthings.iot.dms.dto;


/**
 * @author :  sylar
 * @FileName :  DeviceEventDto
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
public class DeviceEventDto extends AbstractDeviceDto {

    private Long iotDeviceEventId;

    private String eventCode;

    private String eventDesc;

    public Long getIotDeviceEventId() {
        return iotDeviceEventId;
    }

    public void setIotDeviceEventId(Long iotDeviceEventId) {
        this.iotDeviceEventId = iotDeviceEventId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
