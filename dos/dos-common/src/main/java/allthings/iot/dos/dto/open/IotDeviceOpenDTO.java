package allthings.iot.dos.dto.open;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author :  txw
 * @FileName :  IotDeviceDTO
 * @CreateDate :  2018/11/15
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IotDeviceOpenDTO implements Serializable {

    /**
     * 设备唯一编码
     */
    private Long iotDeviceId;

    /**
     * 设备类型唯一编码
     */
    private Long iotDeviceTypeId;

    /**
     * 地址
     */
    private String address;

    /**
     * 业务编码
     */
    private String bizCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型编码
     */
    private String deviceTypeCode;

    /**
     * 固件型号
     */
    private String firmwareModel;

    /**
     * 固件型号
     */
    private String firmwareVersion;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * mac
     */
    private String mac;

    /**
     * 标签名称
     */
    private List<String> tagName;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    /**
     * 所属标签ID
     */
    private List<Long> iotTagIds;

    /**
     * 在线状态
     */
    private Boolean connected;

    /**
     * 设备扩展属性
     */
    private List<Map<String, String>> extendProperties;

    /**
     * 机构
     */
    private String agencyName;

    @JsonIgnore
    private Integer connectStatus;

    public IotDeviceOpenDTO() {
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Integer getConnectStatus() {
        return connectStatus;
    }

    public void getConnectStatus(Integer connectStatus) {
        this.connectStatus = connected ? 1 : 0;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<String> getTagName() {
        return tagName;
    }

    public void setTagName(List<String> tagName) {
        this.tagName = tagName;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public List<Long> getIotTagIds() {
        return iotTagIds;
    }

    public void setIotTagIds(List<Long> iotTagIds) {
        this.iotTagIds = iotTagIds;
    }

    public List<Map<String, String>> getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(List<Map<String, String>> extendProperties) {
        this.extendProperties = extendProperties;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
