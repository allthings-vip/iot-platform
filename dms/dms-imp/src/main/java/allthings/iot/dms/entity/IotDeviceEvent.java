package allthings.iot.dms.entity;


import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author :  sylar
 * @FileName :  IotDeviceEvent
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(appliesTo = "IotDeviceEvent", comment = "设备事件表")
public class IotDeviceEvent extends AbstractDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceEventId'")
    private Long iotDeviceEventId;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '事件编码'")
    private String eventCode;

    @Column(columnDefinition = " varchar(255) comment '事件描述'")
    private String eventDesc;

    public Long getIotDeviceEventId() {
        return iotDeviceEventId;
    }

    public void setIotDeviceEventId(Long iotDeviceEventId) {
        this.iotDeviceEventId = iotDeviceEventId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
