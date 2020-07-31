package allthings.iot.dos.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotDeviceQueryDTO
 * @CreateDate :  2018-5-8
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
public class IotDeviceQueryDTO implements Serializable {
    /**
     * 设备唯一编码
     */
    private Long iotDeviceId;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 设备类型唯一编码
     */
    private Long iotDeviceTypeId;

    /**
     * 设备类型名称
     */
    private String deviceTypeName;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 厂商编码
     */
    private String bizCode;

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
     * 连接状态
     */
    private Boolean connected;

    /**
     * 最近一次连接时间
     */
    private Long latestConnectDatetime;

    /**
     * 最近一次连接时间
     */
    private Long latestDisconnectDatetime;

    /**
     * 创建时间
     */
    private Long inputDate;

    /**
     * 用户名
     */
    private String username;

    /**
     * 最新数据上报时间
     */
    private Long latestUploadDatetime;

    /**
     * 创建人
     */
    private Long createOperatorId;

    /**
     * 创建人
     */
    private String createOperatorName;

    /**
     * 地址
     */
    private String address;

    /**
     * 启用
     */
    private Boolean enabled;

    /**
     * deviceID
     */
    private String deviceId;

    /**
     * 备注
     */
    private String description;

    /**
     * 所属标签ID
     */
    private List<Long> iotTagIds;

    /**
     * 注册状态
     */
    private Boolean registerStatus;

    /**
     * 扩展属性
     */
    private List<Map<String, String>> extendProperties;

    private String deviceTypeCode;

    @JsonIgnore
    private String extendPropertiesAlias;
    /**
     * 机构名称
     */
    private String agencyName;

    private String protocolCode;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getOperator() {
        return this.username;
    }

    public void setOperator(String operator) {
        this.username = operator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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

    public Long getLatestConnectDatetime() {
        return latestConnectDatetime;
    }

    public void setLatestConnectDatetime(Date latestConnectDatetime) {
        if (latestConnectDatetime != null) {
            this.latestConnectDatetime = latestConnectDatetime.getTime();
        }
    }

    public Long getLatestDisconnectDatetime() {
        return latestDisconnectDatetime;
    }

    public void setLatestDisconnectDatetime(Date latestDisconnectDatetime) {
        if (latestDisconnectDatetime != null) {
            this.latestDisconnectDatetime = latestDisconnectDatetime.getTime();
        }
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        if (inputDate != null) {
            this.inputDate = inputDate.getTime();
        }
    }

    public Long getLatestUploadDatetime() {
        return latestUploadDatetime;
    }

    public void setLatestUploadDatetime(Long latestUploadDatetime) {
        this.latestUploadDatetime = latestUploadDatetime;
    }

    public Long getCreateOperatorId() {
        return createOperatorId;
    }

    public void setCreateOperatorId(Long createOperatorId) {
        this.createOperatorId = createOperatorId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

    public List<Long> getIotTagIds() {
        return iotTagIds;
    }

    public void setIotTagIds(List<Long> iotTagIds) {
        this.iotTagIds = iotTagIds;
    }

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public List<Map<String, String>> getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(List<Map<String, String>> extendProperties) {
        this.extendProperties = extendProperties;
    }

    @JsonIgnore
    public String getExtendPropertiesAlias() {
        return extendPropertiesAlias;
    }

    @JsonIgnore
    public void setExtendPropertiesAlias(String extendPropertiesAlias) {
        try {
            List<Map<String, String>> mapList = Lists.newArrayList();
            if (!StringUtils.isBlank(extendPropertiesAlias)) {
                ObjectMapper mapper = new ObjectMapper();
                mapList = mapper.readValue(extendPropertiesAlias, new TypeReference<List<Map<String, String>>>() {
                });
            }
            this.extendProperties = mapList;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    @Override
    public String toString() {
        return "IotDeviceQueryDTO{" +
                "iotDeviceId=" + iotDeviceId +
                ", iotProjectId=" + iotProjectId +
                ", projectName='" + projectName + '\'' +
                ", iotDeviceTypeId=" + iotDeviceTypeId +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", bizCode='" + bizCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", mac='" + mac + '\'' +
                ", firmwareModel='" + firmwareModel + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", connected=" + connected +
                ", latestConnectDatetime=" + latestConnectDatetime +
                ", inputDate=" + inputDate +
                ", username='" + username + '\'' +
                ", latestUploadDatetime=" + latestUploadDatetime +
                ", createOperatorId=" + createOperatorId +
                ", createOperatorName='" + createOperatorName + '\'' +
                ", address='" + address + '\'' +
                ", enabled=" + enabled +
                ", deviceId='" + deviceId + '\'' +
                ", description='" + description + '\'' +
                ", iotTagIds=" + iotTagIds +
                ", registerStatus=" + registerStatus +
                ", extendProperties='" + extendProperties + '\'' +
                '}';
    }
}
