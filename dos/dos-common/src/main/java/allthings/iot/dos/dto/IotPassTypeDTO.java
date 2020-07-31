package allthings.iot.dos.dto;

/**
 * @author luhao
 * @date 2020-2-17 10:39
 */
public class IotPassTypeDTO {

    /**
     * 通道类型唯一编码
     */
    private Long iotPassTypeId;

    /**
     * 设备类型唯一编码
     */
    private Long iotDeviceTypeId;

    /**
     * 通道类型编码
     */
    private String passTypeCode;

    /**
     * 通道类型名称
     */
    private String passTypeName;

    /**
     * 协议id
     */
    private String iotProtocolId;

    public Long getIotPassTypeId() {
        return iotPassTypeId;
    }

    public void setIotPassTypeId(Long iotPassTypeId) {
        this.iotPassTypeId = iotPassTypeId;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public String getPassTypeCode() {
        return passTypeCode;
    }

    public void setPassTypeCode(String passTypeCode) {
        this.passTypeCode = passTypeCode;
    }

    public String getPassTypeName() {
        return passTypeName;
    }

    public void setPassTypeName(String passTypeName) {
        this.passTypeName = passTypeName;
    }

    public String getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(String iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }
}
