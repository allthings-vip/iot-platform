package allthings.iot.das.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author :  sylar
 * @FileName :  ServerConnectEvent
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
public class ServerConnectEvent extends ApplicationEvent {

    private String dasNodeId;
    private String ip;
    private int port;
    private boolean connected;

    public ServerConnectEvent(Object source, String dasNodeId, String ip, int port, boolean connected) {
        super(source);
        this.dasNodeId = dasNodeId;
        this.ip = ip;
        this.port = port;
        this.connected = connected;
    }

    public String getDasNodeId() {
        return dasNodeId;
    }

    public void setDasNodeId(String dasNodeId) {
        this.dasNodeId = dasNodeId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
