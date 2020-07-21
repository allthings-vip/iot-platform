package allthings.iot.dms.dto;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  DeviceStatusDto
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class DeviceStatusDto implements Serializable {

    private String deviceId;

    private String nodeId;

    private String terminalIp;

    private boolean connected;

    private Long onlineTime = System.currentTimeMillis();

    private Long offlineTime;

    public DeviceStatusDto() {
    }

    public DeviceStatusDto(String deviceId, String nodeId, String terminalIp, boolean connected) {
        this.deviceId = deviceId;
        this.nodeId = nodeId;
        this.terminalIp = terminalIp;
        this.connected = connected;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Long getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Long offlineTime) {
        this.offlineTime = offlineTime;
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
