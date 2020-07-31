package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tyf
 * @date 2019/02/25 16:26
 */
@Entity
@Table(name = "iot_dos_device_pass")
public class IotDevicePass extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '设备通道唯一编码' ", name = "iot_dos_device_pass_id")
    private Long iotDevicePassId;

    @Column(columnDefinition = " varchar(500) not null comment '通道编码' ", name = "pass_code")
    private String passCode;

    @Column(columnDefinition = "varchar(500) not null comment '通道名称' ", name = "pass_name")
    private String passName;

    @Column(columnDefinition = "varchar(500) not null comment '通道id' ", name = "pass_id")
    private String passId;

    @Column(columnDefinition = "text comment '扩展属性' ", name = "extend_properties")
    private String extendProperties;

    @Column(columnDefinition = "int(20) not null comment '通道类型' ", name = "iot_dos_pass_type_id")
    private Long iotPassTypeId;

    @Column(columnDefinition = "int(20) not null comment '设备唯一编码' ", name = "iot_dos_device_id")
    private Long iotDeviceId;

    public Long getIotDevicePassId() {
        return iotDevicePassId;
    }

    public void setIotDevicePassId(Long iotDevicePassId) {
        this.iotDevicePassId = iotDevicePassId;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(String extendProperties) {
        this.extendProperties = extendProperties;
    }

    public Long getIotPassTypeId() {
        return iotPassTypeId;
    }

    public void setIotPassTypeId(Long iotPassTypeId) {
        this.iotPassTypeId = iotPassTypeId;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }
}
