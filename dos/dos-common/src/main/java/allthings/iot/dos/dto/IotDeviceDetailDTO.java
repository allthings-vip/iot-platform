package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-05-29 16:28
 */

public class IotDeviceDetailDTO implements Serializable {
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
     * 设备类型编码
     */
    private String deviceTypeCode;

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
     * 启用标识
     */
    private Boolean isEnabled;

    /**
     * 注册状态
     */
    private Boolean registerStatus;
    /**
     * 所属机构
     */
    private String agencyName;

    /**
     * 协议编码
     */
    private String protocolCode;

    public IotDeviceDetailDTO(Long iotDeviceId, Long iotDeviceTypeId, String deviceTypeName, String deviceTypeCode,
                              Long iotProjectId, String deviceCode, String deviceName, Boolean isEnabled,
                              Boolean registerStatus, String agencyName) {
        this.iotDeviceId = iotDeviceId;
        this.iotDeviceTypeId = iotDeviceTypeId;
        this.deviceTypeName = deviceTypeName;
        this.deviceTypeCode = deviceTypeCode;
        this.iotProjectId = iotProjectId;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.isEnabled = isEnabled;
        this.registerStatus = registerStatus;
        this.agencyName = agencyName;
    }

    public IotDeviceDetailDTO(Long iotDeviceId, Long iotDeviceTypeId, String deviceTypeName, String deviceTypeCode,
                              Long iotProjectId, String deviceCode, String deviceName, Boolean isEnabled,
                              Boolean registerStatus, String agencyName, String protocolCode) {
        this.iotDeviceId = iotDeviceId;
        this.iotDeviceTypeId = iotDeviceTypeId;
        this.deviceTypeName = deviceTypeName;
        this.deviceTypeCode = deviceTypeCode;
        this.iotProjectId = iotProjectId;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.isEnabled = isEnabled;
        this.registerStatus = registerStatus;
        this.agencyName = agencyName;
        this.protocolCode = protocolCode;
    }

    public IotDeviceDetailDTO() {
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

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }
}
