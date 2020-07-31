package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author tyf
 * @date 2019/02/25 18:23
 */
@Entity
@Table(name = "iot_dos_logger")
public class IotLogger extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '日志唯一编码' ", name = "iot_dos_logger_id")
    private Long iotLoggerId;

    @Column(columnDefinition = "timeStamp not null comment '日志时间' ", name = "logger_time")
    private Date loggerTime;

    @Column(columnDefinition = "varchar(500) not null comment '日志操作人' ", name = "iot_dos_user_id")
    private Long iotUserId;

    @Column(columnDefinition = "text comment '日志内容' ", name = "logger_content")
    private String loggerContent;

    @Column(columnDefinition = "int(20) not null comment '日志类型' ", name = "iot_dos_logger_type_id")
    private Long iotLoggerTypeId;

    public Long getIotLoggerId() {
        return iotLoggerId;
    }

    public void setIotLoggerId(Long iotLoggerId) {
        this.iotLoggerId = iotLoggerId;
    }

    public Date getLoggerTime() {
        return loggerTime;
    }

    public void setLoggerTime(Date loggerTime) {
        this.loggerTime = loggerTime;
    }

    public Long getIotUserId() {
        return iotUserId;
    }

    public void setIotUserId(Long iotUserId) {
        this.iotUserId = iotUserId;
    }

    public String getLoggerContent() {
        return loggerContent;
    }

    public void setLoggerContent(String loggerContent) {
        this.loggerContent = loggerContent;
    }

    public Long getIotLoggerTypeId() {
        return iotLoggerTypeId;
    }

    public void setIotLoggerTypeId(Long iotLoggerTypeId) {
        this.iotLoggerTypeId = iotLoggerTypeId;
    }
}
