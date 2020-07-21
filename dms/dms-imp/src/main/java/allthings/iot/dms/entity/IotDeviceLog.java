package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author :  sylar
 * @FileName :  IotDeviceLog
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(appliesTo = "IotDeviceLog", comment = "设备日志表")
public class IotDeviceLog extends AbstractDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceLogId' ")
    private Long iotDeviceLogId;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '日志类型' ")
    private String logType;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '日志内容' ")
    private String logContent;

    public Long getIotDeviceLogId() {
        return iotDeviceLogId;
    }

    public void setIotDeviceLogId(Long iotDeviceLogId) {
        this.iotDeviceLogId = iotDeviceLogId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
}
