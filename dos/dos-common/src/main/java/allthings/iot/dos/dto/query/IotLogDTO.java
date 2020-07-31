package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @author :  luhao
 * @FileName :  IotLogDTO
 * @CreateDate :  2018-5-30
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
public class IotLogDTO extends AbstractIotDosDTO {
    /**
     * 操作时间
     */
    private Long loggerTime;
    /**
     * 日志内容
     */
    private String logContent;
    /**
     * 日志类型
     */
    private String logTypeCode;
    /**
     * 用户名
     */
    private String username;

    private Long userId;

    private String associationType;

    private Long associationId;

    private Long iotProjectId;

    private String deviceCode;

    private Long deviceId;

    public Long getLoggerTime() {
        if (loggerTime == null) {
            loggerTime = System.currentTimeMillis();
        }
        return loggerTime;
    }

    public void setLoggerTime(Long loggerTime) {
        this.loggerTime = loggerTime;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogTypeCode() {
        return logTypeCode;
    }

    public void setLogTypeCode(String logTypeCode) {
        this.logTypeCode = logTypeCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
