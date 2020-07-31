package allthings.iot.dos.dto.query;

/**
 * @author tyf
 * @date 2019/03/11 9:12
 */
public class IotDosDeviceEventQueryListDto extends AbstractIotDosQueryListDto {

    /**
     * 设备id
     */
    private Long iotDeviceId;

    /**
     * 项目id
     */
    private Long iotProjectId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
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
}
