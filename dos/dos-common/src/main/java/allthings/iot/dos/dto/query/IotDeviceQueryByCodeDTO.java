package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @author :  txw
 * @FileName :  IotDeviceQueryByCodeDTO
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
public class IotDeviceQueryByCodeDTO extends AbstractIotDosDTO {

    private Long iotProjectId;

    private String deviceCode;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
