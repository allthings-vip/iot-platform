package allthings.iot.dms.entity;


import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author :  sylar
 * @FileName :  IotDeviceAlarm
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
@Table(appliesTo = "IotDeviceAlarm", comment = "设备报警表")
public class IotDeviceAlarm extends AbstractDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceAlarmId' ")
    private Long iotDeviceAlarmId;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '报警编码' ")
    private String alarmCode;

    @Column(columnDefinition = " varchar(255) comment '报警描述' ")
    private String alarmDesc;

    public Long getIotDeviceAlarmId() {
        return iotDeviceAlarmId;
    }

    public void setIotDeviceAlarmId(Long iotDeviceAlarmId) {
        this.iotDeviceAlarmId = iotDeviceAlarmId;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmDesc() {
        return alarmDesc;
    }

    public void setAlarmDesc(String alarmDesc) {
        this.alarmDesc = alarmDesc;
    }
}
