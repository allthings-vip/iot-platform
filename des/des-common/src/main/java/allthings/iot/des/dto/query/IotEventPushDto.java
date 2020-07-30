package allthings.iot.des.dto.query;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2019/03/07 10:46
 */
public class IotEventPushDto implements Serializable {

    private String deviceCode;

    private String eventSource;

    private Long eventTime;

    private String eventDescription;

    private List<IotDesEventData> eventData;

    private String image;

    private String video;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public List<IotDesEventData> getEventData() {
        return eventData;
    }

    public void setEventData(List<IotDesEventData> eventData) {
        this.eventData = eventData;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "IotEventPushDto{" +
                "deviceCode='" + deviceCode + '\'' +
                ", eventSource='" + eventSource + '\'' +
                ", eventTime=" + eventTime +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventData=" + eventData +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
