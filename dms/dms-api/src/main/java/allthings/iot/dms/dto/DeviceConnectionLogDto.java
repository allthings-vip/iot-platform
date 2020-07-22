package allthings.iot.dms.dto;

/**
 * @author :  sylar
 * @FileName :  DeviceConnectionLogDto
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
public class DeviceConnectionLogDto extends AbstractDeviceDto {

    private Long iotDeviceConnectionLogId;

    private String nodeId;

    private String terminalIp;

    private boolean connected;

    public Long getIotDeviceConnectionLogId() {
        return iotDeviceConnectionLogId;
    }

    public void setIotDeviceConnectionLogId(Long iotDeviceConnectionLogId) {
        this.iotDeviceConnectionLogId = iotDeviceConnectionLogId;
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
