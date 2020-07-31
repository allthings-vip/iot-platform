package allthings.iot.dos.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotDeviceType
 * @CreateDate :  2018/4/26
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
@DynamicUpdate
@Table(name = "iot_dos_devicetype")
public class IotDeviceType extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '设备类型唯一编码' ", name = "iot_dos_devicetype_id")
    private Long iotDeviceTypeId;

    @Column(columnDefinition = " varchar(500)  comment '设备类型编码' ", name = "devicetype_code", nullable = false)
    private String deviceTypeCode;

    @Column(columnDefinition = " varchar(500)  comment '设备类型名称' ", name = "devicetype_name", nullable = false)
    private String deviceTypeName;

    @Column(columnDefinition = " int(20) comment '设备接入协议' ", name = "iot_dos_protocol_id", nullable = false)
    private Long iotProtocolId;

    @Column(columnDefinition = " varchar(500)  comment '简介' ", name = "description", nullable = false)
    private String description;

    @Column(columnDefinition = " varchar(500)  comment '图片' ", name = "image_url", nullable = false)
    private String imageUrl;

    @Column(columnDefinition = " tinyint(1) not null default 1 COMMENT '删除标识 0=禁用 1=启用'", name = "is_enabled")
    private Boolean isEnabled;

    @Column(columnDefinition = "int(20) comment '项目唯一编码'", name = "iot_dos_project_id", nullable = false)
    private Long iotProjectId;

    @Column(columnDefinition = " varchar(500)  comment '厂商名称' ", name = "manufacturer")
    private String manufacturer;

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public Long getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(Long iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
