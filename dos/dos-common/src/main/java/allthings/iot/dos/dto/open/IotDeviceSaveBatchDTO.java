package allthings.iot.dos.dto.open;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotDeviceSaveBatchDTO
 * @CreateDate :  2019/1/11
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
public class IotDeviceSaveBatchDTO implements Serializable {

    List<IotDeviceOpenDTO> devices;

    private Long iotProjectId;

    public List<IotDeviceOpenDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<IotDeviceOpenDTO> devices) {
        this.devices = devices;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
