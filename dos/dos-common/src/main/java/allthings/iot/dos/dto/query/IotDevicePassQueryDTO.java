package allthings.iot.dos.dto.query;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-04-16 10:12
 */

public class IotDevicePassQueryDTO extends AbstractIotDosQueryListDto {
    /**
     * 项目id
     */
    private Long iotProjectId;

    /**
     * 通道ID
     */
    private Long iotDevicePassId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 云台控制命令
     */
    private Integer command;

    /**
     * 速度
     */
    private Integer speed;

    /**
     * 通道编码
     */
    private String passCode;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public Long getIotDevicePassId() {
        return iotDevicePassId;
    }

    public void setIotDevicePassId(Long iotDevicePassId) {
        this.iotDevicePassId = iotDevicePassId;
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

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }
}
