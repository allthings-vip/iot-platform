package allthings.iot.dos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tyf
 * @date 2019/02/25 18:33
 */
@Entity
@Table(name = "iot_dos_logger_relation")
public class IotLoggerRelation extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '日志关联关系唯一编码' ", name = "iot_dos_logger_relation_id")
    private Long iotLoggerRelationId;

    @Column(columnDefinition = "int(20) not null comment '日志id' ", name = "iot_dos_logger_id")
    private Long iotLoggerId;

    @Column(columnDefinition = "varchar(500) not null comment '日志关联类型' ", name = "association_type")
    private String associationType;

    @Column(columnDefinition = "int(20) not null comment '日志关联类型id' ", name = "association_id")
    private Long associationId;

    @Column(columnDefinition = "int(20) not null comment '所属项目id' ", name = "iot_dos_project_id")
    private Long iotProjectId;

    public Long getIotLoggerRelationId() {
        return iotLoggerRelationId;
    }

    public void setIotLoggerRelationId(Long iotLoggerRelationId) {
        this.iotLoggerRelationId = iotLoggerRelationId;
    }

    public Long getIotLoggerId() {
        return iotLoggerId;
    }

    public void setIotLoggerId(Long iotLoggerId) {
        this.iotLoggerId = iotLoggerId;
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
