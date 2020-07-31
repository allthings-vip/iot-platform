package allthings.iot.dos.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotDeviceDTO
 * @CreateDate :  2018/5/4
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
public class IotDeviceDTO extends AbstractIotDosDTO {
    /**
     * 设备唯一编码
     */
    private Long iotDeviceId;

    /**
     * 设备类型唯一编码
     */
    private Long iotDeviceTypeId;

    /**
     * 设备类型名称
     */
    private String deviceTypeName;

    /**
     * 项目唯一编码
     */
    private Long iotProjectId;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 固件型号
     */
    private String firmwareModel;

    /**
     * 固件型号
     */
    private String firmwareVersion;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 启用标识
     */
    private Boolean isEnabled;

    /**
     * 行号
     */
    private Integer rowNum;

    /**
     * 业务编码
     */
    private String bizCode;

    /**
     * 注册状态
     */
    private Boolean registerStatus;

    /**
     * 协议名称
     */
    private String protocolName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 注册时间
     */
    private Long registerDate;

    /**
     * 备注
     */
    private String description;

    /**
     * 所属标签ID
     */
    private List<Long> iotTagIds;

    private Boolean connected;

    /**
     * 设备编码的MD5
     */
    private String deviceId;

    /**
     * 是否为批量
     */
    private Boolean isBatch;

    /**
     * 扩展属性
     */
    private List<Map> extendProperties;

    /**
     * 所属机构
     */
    private String agencyName;

    public IotDeviceDTO() {
    }

    public IotDeviceDTO(String deviceTypeName, String deviceCode, String deviceName, String bizCode, Long iotDeviceId) {
        this.deviceTypeName = deviceTypeName;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.bizCode = bizCode;
        this.iotDeviceId = iotDeviceId;
    }

    public IotDeviceDTO(Long iotDeviceId, String deviceTypeName, String deviceCode, String bizCode,
                        String protocolName, String projectName, Date registerDate) {
        this.iotDeviceId = iotDeviceId;
        this.deviceTypeName = deviceTypeName;
        this.deviceCode = deviceCode;
        this.bizCode = bizCode;
        this.protocolName = protocolName;
        this.projectName = projectName;
        this.registerDate = registerDate.getTime();
    }

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

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
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
        if (longitude != null && longitude == -1000) {
            this.longitude = null;
        } else {
            this.longitude = longitude;
        }

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        if (latitude != null && latitude == -1000) {
            this.latitude = null;
        } else {
            this.latitude = latitude;
        }
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getRegisterDate() {
        return registerDate;
    }

    //    public void setRegisterDate(Long registerDate) {
//        this.registerDate = registerDate;
//    }
    public void setRegisterDate(Date registerDates) {
        if (registerDates != null) {
            this.registerDate = registerDates.getTime();
        }
    }

    public List<Long> getIotTagIds() {
        return iotTagIds;
    }

    public void setIotTagIds(List<Long> iotTagIds) {
        this.iotTagIds = iotTagIds;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getBatch() {
        return isBatch;
    }

    public void setBatch(Boolean batch) {
        isBatch = batch;
    }

    public List<Map> getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(List<Map> extendProperties) {
        this.extendProperties = extendProperties;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
