package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceStatusDTO
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
public class IotDeviceStatusDTO extends AbstractIotDosDTO implements Serializable {
    /**
     * 设备唯一编码
     */
    private Long[] iotDeviceIds;
    /**
     * 状态
     */
    private Boolean enabled;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    public Long[] getIotDeviceIds() {
        return iotDeviceIds;
    }

    public void setIotDeviceIds(Long[] iotDeviceIds) {
        this.iotDeviceIds = iotDeviceIds;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
