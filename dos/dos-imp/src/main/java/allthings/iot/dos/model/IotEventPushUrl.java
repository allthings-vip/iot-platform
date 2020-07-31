package allthings.iot.dos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tyf
 * @date 2019/03/07 15:57
 */
@Entity
@Table(name = "iot_dos_event_push_url")
public class IotEventPushUrl extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '事件推送地址唯一编码' ", name = "iot_dos_event_push_url_id")
    private Long iotEventPushUrlId;

    @Column(columnDefinition = "int(20) comment '项目id' ", name = "iot_dos_project_id")
    private Long iotProjectId;

    @Column(columnDefinition = "varchar(500) comment '推送地址' ", name = "push_url")
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
