package allthings.iot.dos.dto.open;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotDeviceTypeDeleteByCodeDTO
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
public class IotDeviceTypeDeleteByCodeDTO implements Serializable {

    /**
     * 设备类型编码
     */
    private List<String> deviceTypeCodes;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    public IotDeviceTypeDeleteByCodeDTO() {
    }

    public List<String> getDeviceTypeCodes() {
        return deviceTypeCodes;
    }

    public void setDeviceTypeCodes(List<String> deviceTypeCodes) {
        this.deviceTypeCodes = deviceTypeCodes;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
