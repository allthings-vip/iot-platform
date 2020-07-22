package allthings.iot.dms.entity;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  IotDeviceStatus
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class IotDeviceStatus implements Serializable {

    private String deviceId;

    private String nodeId;

    private String terminalIp;

    private boolean connected;

    public IotDeviceStatus() {
    }

    public IotDeviceStatus(String deviceId, String nodeId, String terminalIp, boolean connected) {
        this.deviceId = deviceId;
        this.nodeId = nodeId;
        this.terminalIp = terminalIp;
        this.connected = connected;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
