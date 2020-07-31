package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :  txw
 * @FileName :  IotDeviceTypeSimpleDTO
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
public class IotDeviceTypeSimpleDTO implements Serializable {

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
     * 创建时间
     */
    private Long inputDate;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 接入协议名称
     */
    private String protocolName;

    /**
     * 接入协议编码
     */
    private String protocolCode;

    /**
     * 厂商
     */
    private String manufacturer;

    public IotDeviceTypeSimpleDTO(String description, String deviceTypeCode, String deviceTypeName, String imageUrl,
                                  Date inputDate, String projectName, String protocolName, String protocolCode,
                                  String manufacturer) {
        this.description = description;
        this.deviceTypeCode = deviceTypeCode;
        this.deviceTypeName = deviceTypeName;
        this.imageUrl = imageUrl;
        if (inputDate != null) {
            this.inputDate = inputDate.getTime();
        }
        this.projectName = projectName;
        this.protocolName = protocolName;
        this.protocolCode = protocolCode;
        this.manufacturer = manufacturer;
    }

    public IotDeviceTypeSimpleDTO() {
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

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        if (inputDate != null) {
            this.inputDate = inputDate.getTime();
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
