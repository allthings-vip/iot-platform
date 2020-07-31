package allthings.iot.dos.dto;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotDeviceRegisterBatchDTO
 * @CreateDate :  2018/11/7
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
public class IotDeviceRegisterBatchDTO extends AbstractIotDosDTO {

    private List<Long> iotDeviceIds;

    private Long iotDeviceTypeId;

    private Long iotProjectId;

    public List<Long> getIotDeviceIds() {
        return iotDeviceIds;
    }

    public void setIotDeviceIds(List<Long> iotDeviceIds) {
        this.iotDeviceIds = iotDeviceIds;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
