package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tyf
 * @date 2019/03/11 15:33
 */
@Entity
@Table(name = "iot_dos_logger_type")
public class IotLoggerType extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '日志类型唯一编码' ", name = "iot_dos_logger_type_id")
    private Long iotLoggerTypeId;

    @Column(columnDefinition = "varchar(500) not null comment '日志类型编码' ", name = "logger_type_code")
    private String loggerTypeCode;

    @Column(columnDefinition = "varchar(500) not null comment '日志类型名称' ", name = "logger_type_name")
    private String loggerTypeName;

    public Long getIotLoggerTypeId() {
        return iotLoggerTypeId;
    }

    public void setIotLoggerTypeId(Long iotLoggerTypeId) {
        this.iotLoggerTypeId = iotLoggerTypeId;
    }

    public String getLoggerTypeCode() {
        return loggerTypeCode;
    }

    public void setLoggerTypeCode(String loggerTypeCode) {
        this.loggerTypeCode = loggerTypeCode;
    }

    public String getLoggerTypeName() {
        return loggerTypeName;
    }

    public void setLoggerTypeName(String loggerTypeName) {
        this.loggerTypeName = loggerTypeName;
    }
}
