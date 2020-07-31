package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tyf
 * @date 2019/03/11 19:08
 */
public class IotDeviceLoggerListDto implements Serializable {

    private Long loggerTime;

    private String userName;

    private String loggerTypeName;

    private String loggerContent;

    private Long iotDeviceLoggerId;

    private Long iotLoggerTypeId;

    private Long iotUserId;

    public Long getLoggerTime() {
        return loggerTime;
    }

    public void setLoggerTime(Date loggerTime) {
        this.loggerTime = loggerTime.getTime();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoggerTypeName() {
        return loggerTypeName;
    }

    public void setLoggerTypeName(String loggerTypeName) {
        this.loggerTypeName = loggerTypeName;
    }

    public String getLoggerContent() {
        return loggerContent;
    }

    public void setLoggerContent(String loggerContent) {
        this.loggerContent = loggerContent;
    }

    public Long getIotDeviceLoggerId() {
        return iotDeviceLoggerId;
    }

    public void setIotDeviceLoggerId(Long iotDeviceLoggerId) {
        this.iotDeviceLoggerId = iotDeviceLoggerId;
    }

    public Long getIotLoggerTypeId() {
        return iotLoggerTypeId;
    }

    public void setIotLoggerTypeId(Long iotLoggerTypeId) {
        this.iotLoggerTypeId = iotLoggerTypeId;
    }

    public Long getIotUserId() {
        return iotUserId;
    }

    public void setIotUserId(Long iotUserId) {
        this.iotUserId = iotUserId;
    }
}
