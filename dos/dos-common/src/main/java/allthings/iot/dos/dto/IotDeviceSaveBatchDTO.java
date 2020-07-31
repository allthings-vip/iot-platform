package allthings.iot.dos.dto;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotDeviceSaveBatchDTO
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
public class IotDeviceSaveBatchDTO extends AbstractIotDosDTO {

    private Long iotProjectId;

    private List<IotDeviceDTO> iotDeviceList;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public List<IotDeviceDTO> getIotDeviceList() {
        return iotDeviceList;
    }

    public void setIotDeviceList(List<IotDeviceDTO> iotDeviceList) {
        this.iotDeviceList = iotDeviceList;
    }
}
