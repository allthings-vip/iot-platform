package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotDeviceTypeSaveDTO
 * @CreateDate :  2018/11/16
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
public class IotDeviceTypeSaveDTO implements Serializable {

    /**
     * 设备类型ID
     */
    private Long iotDeviceTypeId;

    /**
     * 描述
     */
    private String description;

    /**
     * 设备类型编码
     */
    private String deviceTypeCode;

    /**
     * 设备类型名称
     */
    private String deviceTypeName;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 协议编码
     */
    private String protocolCode;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    /**
     * 厂商
     */
    private String manufacturer;

    public IotDeviceTypeSaveDTO() {
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
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
