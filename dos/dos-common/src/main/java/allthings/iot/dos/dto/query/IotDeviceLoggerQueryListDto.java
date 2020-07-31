package allthings.iot.dos.dto.query;

/**
 * @author tyf
 * @date 2019/03/11 16:03
 */
public class IotDeviceLoggerQueryListDto extends AbstractIotDosQueryListDto {

    /**
     * 项目id
     */
    private Long iotProjectId;

    /**
     * 设备id
     */
    private Long iotDeviceId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 日志类型编码
     */
    private String loggerTypeCode;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getLoggerTypeCode() {
        return loggerTypeCode;
    }

    public void setLoggerTypeCode(String loggerTypeCode) {
        this.loggerTypeCode = loggerTypeCode;
    }
}
