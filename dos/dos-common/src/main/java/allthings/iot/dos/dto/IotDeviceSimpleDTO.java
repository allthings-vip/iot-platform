package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceSimpleDTO
 * @CreateDate :  2018-5-30
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
public class IotDeviceSimpleDTO implements Serializable {
    /**
     * 设备唯一编码
     */
    private Long iotDeviceId;
    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 协议编码
     */
    private String protocolCode;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    public IotDeviceSimpleDTO() {
    }

    public IotDeviceSimpleDTO(Long iotDeviceId, String deviceCode, String protocolCode) {
        this.iotDeviceId = iotDeviceId;
        this.deviceCode = deviceCode;
        this.protocolCode = protocolCode;
    }

    public IotDeviceSimpleDTO(Long iotDeviceId, String deviceCode, Long iotProjectId) {
        this.iotDeviceId = iotDeviceId;
        this.deviceCode = deviceCode;
        this.iotProjectId = iotProjectId;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
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
}
