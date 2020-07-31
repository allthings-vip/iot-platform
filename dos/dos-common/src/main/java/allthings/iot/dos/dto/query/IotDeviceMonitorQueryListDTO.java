package allthings.iot.dos.dto.query;

/**
 * @author :  txw
 * @FileName :  IotDeviceMonitorQueryListDTO
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
public class IotDeviceMonitorQueryListDTO extends AbstractIotDosQueryListDto {

    private Boolean registerStatus;

    private Long iotProtocolId;

    private String deviceCode;

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Long getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(Long iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
