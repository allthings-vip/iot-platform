package allthings.iot.des.dto.query;

import com.alibaba.fastjson.JSON;
import allthings.iot.des.dto.AbstractIotDesDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/03/04 15:08
 */
public class IotDesEventDetailDto extends AbstractIotDesDto {

    /**
     * 设备事件id
     */
    private Long iotDesDeviceEventId;

    /**
     * 设备id
     */
    private Long iotDosDeviceId;

    /**
     * 事件时间
     */
    private Long eventTime;

    /**
     * 事件源
     */
    private String eventSource;

    /**
     * 事件类型名称
     */
    private String eventTypeName;

    /**
     * 事件类型id
     */
    private Long iotDesEventTypeId;

    /**
     * 事件描述
     */
    private String eventDescription;

    /**
     * 事件数据
     */
    private List<Map> eventData;

    /**
     * 事件附件 --图片
     */
    private String image;

    /**
     * 事件附件 --视频
     */
    private String video;

    public Long getIotDesDeviceEventId() {
        return iotDesDeviceEventId;
    }

    public void setIotDesDeviceEventId(Long iotDesDeviceEventId) {
        this.iotDesDeviceEventId = iotDesDeviceEventId;
    }

    public Long getIotDosDeviceId() {
        return iotDosDeviceId;
    }

    public void setIotDosDeviceId(Long iotDosDeviceId) {
        this.iotDosDeviceId = iotDosDeviceId;
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

    public List<Map> getEventData() {
        return eventData;
    }

    public void setEventData(List<Map> eventData) {
        this.eventData = eventData;
    }

    public void setEventDataAlias(String eventData) {
        this.eventData = JSON.parseArray(eventData, Map.class);

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
}
