package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tyf
 * @date 2019/03/14 19:19
 */
@Entity
@Table(name = "iot_dos_pass_type")
public class IotPassType extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '通道类型唯一编码' ", name = "iot_dos_pass_type_id")
    private Long iotPassTypeId;

    @Column(columnDefinition = "int(20) not null comment '设备类型唯一编码' ", name = "iot_dos_device_type_id")
    private Long iotDeviceTypeId;

    @Column(columnDefinition = "varchar(500) not null comment '通道类型编码' ", name = "pass_type_code")
    private String passTypeCode;

    @Column(columnDefinition = "varchar(500) not null comment '通道类型名称' ", name = "pass_type_name")
    private String passTypeName;

    @Column(columnDefinition = "int(20) not null comment '协议id' ", name = "iot_dos_protocol_id")
    private String iotProtocolId;

    public Long getIotPassTypeId() {
        return iotPassTypeId;
    }

    public void setIotPassTypeId(Long iotPassTypeId) {
        this.iotPassTypeId = iotPassTypeId;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public String getPassTypeCode() {
        return passTypeCode;
    }

    public void setPassTypeCode(String passTypeCode) {
        this.passTypeCode = passTypeCode;
    }

    public String getPassTypeName() {
        return passTypeName;
    }

    public void setPassTypeName(String passTypeName) {
        this.passTypeName = passTypeName;
    }

    public String getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(String iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }
}
