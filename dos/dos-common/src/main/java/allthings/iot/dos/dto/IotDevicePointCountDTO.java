package allthings.iot.dos.dto;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-27 14:07
 */

public class IotDevicePointCountDTO extends AbstractIotDosDTO {
    /**
     * 主键
     */
    private Long iotDevicePointCountId;

    /**
     * 设备Id
     */
    private String deviceId;

    /**
     * 当天之前总数量
     */
    private Long dayBeforeQuantity;

    /**
     * 截至日期
     */
    private Long upDateTime;

    public Long getIotDevicePointCountId() {
        return iotDevicePointCountId;
    }

    public void setIotDevicePointCountId(Long iotDevicePointCountId) {
        this.iotDevicePointCountId = iotDevicePointCountId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDayBeforeQuantity() {
        return dayBeforeQuantity;
    }

    public void setDayBeforeQuantity(Long dayBeforeQuantity) {
        this.dayBeforeQuantity = dayBeforeQuantity;
    }

    public Long getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Long upDateTime) {
        this.upDateTime = upDateTime;
    }
}
