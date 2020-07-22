package allthings.iot.common.msg;

/**
 * DAS 服务器节点连接(上/下线)消息
 */

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class DasConnectionMsg extends AbstractDeviceMsg {

    /**
     * DAS集群节点实例编码
     */
    private String dasNodeId;

    /**
     * 服务器节点连接状态(上/下线)
     */
    private boolean connected;

    private String ip;

    private int port;

    public void setDasInfo(String dasNodeId, String ip, int port, boolean connected) {
        this.dasNodeId = dasNodeId;
        this.ip = ip;
        this.port = port;
        this.connected = connected;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.DasConnection;
    }

    public String getDasNodeId() {
        return dasNodeId;
    }

    public void setDasNodeId(String dasNodeId) {
        this.dasNodeId = dasNodeId;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
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
}
