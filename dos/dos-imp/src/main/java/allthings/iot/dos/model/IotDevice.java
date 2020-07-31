package allthings.iot.dos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotDevice
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
@Table(name = "iot_dos_device")
public class IotDevice extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '设备唯一编码' ", name = "iot_dos_device_id")
    private Long iotDeviceId;

    @Column(columnDefinition = " int(20) comment '设备类型唯一编码' ", nullable = false, name = "iot_dos_devicetype_id")
    private Long iotDeviceTypeId;

    @Column(columnDefinition = " int(20) comment '项目唯一编码' ", nullable = false, name = "iot_dos_project_id")
    private Long iotProjectId;

    @Column(columnDefinition = " varchar(500)  comment '设备编码' ", nullable = false, name = "device_code")
    private String deviceCode;

    @Column(columnDefinition = " varchar(500)  comment '设备名称' ", nullable = false, name = "device_name")
    private String deviceName;

    @Column(columnDefinition = " varchar(500) default '' comment 'mac地址' ", name = "mac")
    private String mac;

    @Column(columnDefinition = " varchar(500) default '' comment '固件型号' ", name = "firmware_model")
    private String firmwareModel;

    @Column(columnDefinition = " varchar(500) default ''  comment '固件版本号' ", name = "firmware_version")
    private String firmwareVersion;

    @Column(columnDefinition = " decimal(18,14) default -1000 comment '经度' ", name = "longitude")
    private Double longitude;

    @Column(columnDefinition = " decimal(18,14) default -1000 comment '纬度' ", name = "latitude")
    private Double latitude;

    @Column(columnDefinition = " tinyint(1) not null default 1 comment '删除标识 0=禁用 1=启用'", name = "is_enabled")
    private Boolean isEnabled;

    @Column(columnDefinition = " varchar(500)  comment '备注' ", nullable = false, name = "description")
    private String description;

    @Column(columnDefinition = " varchar(500)  comment '业务编码' ", name = "biz_code")
    private String bizCode;

    @Column(columnDefinition = " tinyint(1) not null comment '删除标识 0=禁用 1=启用'", name = "register_status")
    private Boolean registerStatus;

    @Column(columnDefinition = " varchar(500) not null  comment 'deviceID' ", name = "device_Id")
    private String deviceId;

    @Column(columnDefinition = " timeStamp COMMENT '注册时间' ", name = "register_date")
    private Date registerDate;

    @Column(columnDefinition = "text default '' comment '扩展属性' ", name = "extend_properties")
    private String extendProperties;

    @Column(columnDefinition = " varchar(500)  comment '所属机构' ", name = "agency_name")
    private String agencyName;

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getFirmwareModel() {
        return firmwareModel;
    }

    public void setFirmwareModel(String firmwareModel) {
        this.firmwareModel = firmwareModel;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(String extendProperties) {
        this.extendProperties = extendProperties;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
