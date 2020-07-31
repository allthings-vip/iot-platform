package allthings.iot.dos.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2018/08/06 15:53
 */
public class IotIovProtocolCodeQueryDto implements Serializable {
    /**
     * 设备id
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
     * 设备编号
     */
    @JsonIgnore
    private String deviceId;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
