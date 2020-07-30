package allthings.iot.des.model;

import java.util.Date;

/**
 * @author tyf
 * @date 2019/03/04 10:38
 */
public class IotDesDeviceEvent extends AbstractIotDesModel {

    /**
     * 设备事件唯一编码
     */
    private Long iotDesDeviceEventId;

    /**
     * 事件发生时间
     */
    private Date eventTime;

    /**
     * 事件源
     */
    private String eventSource;

    /**
     * 设备id
     */
    private Long iotDeviceId;

    /**
     * 事件类型
     */
    private Long iotDesEventTypeId;

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

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public Long getIotDesEventTypeId() {
        return iotDesEventTypeId;
    }

    public void setIotDesEventTypeId(Long iotDesEventTypeId) {
        this.iotDesEventTypeId = iotDesEventTypeId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
