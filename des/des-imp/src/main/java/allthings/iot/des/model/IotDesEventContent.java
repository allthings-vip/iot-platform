package allthings.iot.des.model;

/**
 * @author tyf
 * @date 2019/03/04 10:52
 */
public class IotDesEventContent extends AbstractIotDesModel {

    private Long iotDesEventContentId;

    private Long iotDesDeviceEventId;

    private String eventData;

    private String image;

    private String video;

    public Long getIotDesEventContentId() {
        return iotDesEventContentId;
    }

    public void setIotDesEventContentId(Long iotDesEventContentId) {
        this.iotDesEventContentId = iotDesEventContentId;
    }

    public Long getIotDesDeviceEventId() {
        return iotDesDeviceEventId;
    }

    public void setIotDesDeviceEventId(Long iotDesDeviceEventId) {
        this.iotDesDeviceEventId = iotDesDeviceEventId;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
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
}
