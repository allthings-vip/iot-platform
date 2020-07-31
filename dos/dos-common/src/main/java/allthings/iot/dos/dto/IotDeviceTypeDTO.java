package allthings.iot.dos.dto;

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
public class IotDeviceTypeDTO extends AbstractIotDosDTO {
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
     * 简介
     */
    private String description;
    /**
     * 图片Url
     */
    private String imageUrl;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    /**
     * 厂商名称
     */
    private String manufacturer;

    public IotDeviceTypeDTO() {
    }

    public IotDeviceTypeDTO(Long iotDeviceTypeId, String deviceTypeName) {
        this.iotDeviceTypeId = iotDeviceTypeId;
        this.deviceTypeName = deviceTypeName;
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
