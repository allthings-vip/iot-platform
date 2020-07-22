package allthings.iot.dms.dto;


/**
 * @author :  sylar
 * @FileName :  DeviceAlarmDto
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
public class DeviceAlarmDto extends AbstractDeviceDto {

    private Long iotDeviceAlarmId;

    private String alarmCode;

    private String alarmDesc;

    public Long getIotDeviceAlarmId() {
        return iotDeviceAlarmId;
    }

    public void setIotDeviceAlarmId(Long iotDeviceAlarmId) {
        this.iotDeviceAlarmId = iotDeviceAlarmId;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmDesc() {
        return alarmDesc;
    }

    public void setAlarmDesc(String alarmDesc) {
        this.alarmDesc = alarmDesc;
    }
}
