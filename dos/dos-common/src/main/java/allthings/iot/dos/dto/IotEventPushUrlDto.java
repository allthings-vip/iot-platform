package allthings.iot.dos.dto;

/**
 * @author tyf
 * @date 2019/03/08 9:21
 */
public class IotEventPushUrlDto extends AbstractIotDosDTO {

    private Long iotEventPushUrlId;

    private Long iotProjectId;

    private String pushUrl;

    public Long getIotEventPushUrlId() {
        return iotEventPushUrlId;
    }

    public void setIotEventPushUrlId(Long iotEventPushUrlId) {
        this.iotEventPushUrlId = iotEventPushUrlId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }
}
