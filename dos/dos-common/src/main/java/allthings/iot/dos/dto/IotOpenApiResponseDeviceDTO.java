package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @author Txw
 * @description
 * @date 2019/12/7
 */
public class IotOpenApiResponseDeviceDTO implements Serializable {

    private String deviceCode;
    private String newDeviceCode;
    private String protocolCode;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getNewDeviceCode() {
        return newDeviceCode;
    }

    public void setNewDeviceCode(String newDeviceCode) {
        this.newDeviceCode = newDeviceCode;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }
}
