package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotProjectDeviceTypeDTO
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
public class IotProjectDeviceTypeDTO implements Serializable {
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

    public IotProjectDeviceTypeDTO() {
    }

    public IotProjectDeviceTypeDTO(Long iotDeviceTypeId, String deviceTypeName) {
        this.iotDeviceTypeId = iotDeviceTypeId;
        this.deviceTypeName = deviceTypeName;
    }

    public IotProjectDeviceTypeDTO(Long iotDeviceTypeId, String deviceTypeName, String deviceTypeCode) {
        this.iotDeviceTypeId = iotDeviceTypeId;
        this.deviceTypeName = deviceTypeName;
        this.deviceTypeCode = deviceTypeCode;
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
}
