package allthings.iot.dms.dto;

/**
 * @author :  sylar
 * @FileName :  DeviceLogDto
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
public class DeviceLogDto extends AbstractDeviceDto {

    private Long iotDeviceLogId;

    private String logType;

    private String logContent;

    public Long getIotDeviceLogId() {
        return iotDeviceLogId;
    }

    public void setIotDeviceLogId(Long iotDeviceLogId) {
        this.iotDeviceLogId = iotDeviceLogId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
}
