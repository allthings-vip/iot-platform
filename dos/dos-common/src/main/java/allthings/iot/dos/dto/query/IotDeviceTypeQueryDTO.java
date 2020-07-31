package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeDTO
 * @CreateDate :  2018/4/29
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
public class IotDeviceTypeQueryDTO implements Serializable {
    /**
     * 设备类型唯一编码
     */
    private Long iotDeviceTypeId;
    /**
     * 设备类型编码
     */
    private String deviceTypeCode;
    /**
     * 设备类型名称
     */
    private String deviceTypeName;
    /**
     * 设备接入协议
     */
    private Long iotProtocolId;
    /**
     * 协议名称
     */
    private String protocolName;
    /**
     * 服务器IP域名
     */
    private String serverDomain;
    /**
     * 服务器端口
     */
    private Integer serverPort;
    /**
     * 简介
     */
    private String description;
    /**
     * 图片Url
     */
    private String imageUrl;

    /**
     *
     */
    private Boolean status;

    /**
     * 用户名
     */
    private String username;

    /**
     *
     */
    private Long inputDate;

    /**
     * 厂商
     */
    private String manufacturer;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getInputDate() {
        return this.inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate.getTime();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
