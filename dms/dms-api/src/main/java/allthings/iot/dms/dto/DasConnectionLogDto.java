package allthings.iot.dms.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * @author :  sylar
 * @FileName :  DasConnectionLogDto
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
public class DasConnectionLogDto implements Serializable {
    private Long iotDasConnectionLogId;

    private String nodeId;

    private String nodeIp;

    private int nodePort;

    private boolean connected;

    private Date inputDate;

    private Date updateDate;

    private Date stampDate;

    private boolean isDelete;

    public Long getIotDasConnectionLogId() {
        return iotDasConnectionLogId;
    }

    public void setIotDasConnectionLogId(Long iotDasConnectionLogId) {
        this.iotDasConnectionLogId = iotDasConnectionLogId;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
