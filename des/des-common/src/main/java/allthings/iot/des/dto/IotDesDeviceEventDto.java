package allthings.iot.des.dto;

/**
 * @author tyf
 * @date 2019/03/04 15:02
 */
public class IotDesDeviceEventDto extends AbstractIotDesDto {

    /**
     * 设备事件id
     */
    private Long iotDesDeviceEventId;

    /**
     * 事件时间
     */
    private Long eventTime;

    /**
     * 事件源
     */
    private String eventSource;

    /**
     * 事件类型id
     */
    private Long eventTypeId;

    /**
     * 事件描述
     */
    private String eventDescription;

    public Long getIotDesDeviceEventId() {
        return iotDesDeviceEventId;
    }

    public void setIotDesDeviceEventId(Long iotDesDeviceEventId) {
        this.iotDesDeviceEventId = iotDesDeviceEventId;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
