package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeStatusDTO
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
public class IotDeviceTypeStatusDTO implements Serializable {
    /**
     * 设备类型唯一编码
     */
    private Long[] iotDeviceTypeIds;
    /**
     * 状态
     */
    private Integer status;

    public Long[] getIotDeviceTypeIds() {
        return iotDeviceTypeIds;
    }

    public void setIotDeviceTypeIds(Long[] iotDeviceTypeIds) {
        this.iotDeviceTypeIds = iotDeviceTypeIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
