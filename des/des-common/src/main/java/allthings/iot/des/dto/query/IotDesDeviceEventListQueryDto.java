package allthings.iot.des.dto.query;


import allthings.iot.des.dto.AbstractIotDesDto;

import java.util.Date;

/**
 * @author tyf
 * @date 2019/03/06 14:48
 */
public class IotDesDeviceEventListQueryDto extends AbstractIotDesDto {

    private Long iotDesDeviceEventId;

    private Long eventTime;

    private String eventSource;

    private String eventTypeName;

    private Long iotDesEventTypeId;

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

    public void setEventTimeAlias(Date eventTime) {
        this.eventTime = eventTime.getTime();
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
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
