package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author :  sylar
 * @FileName :  IotDasConnectionLog
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
@Entity
@Table(appliesTo = "IotDasConnectionLog", comment = "设备接入服务连接日志表")
public class IotDasConnectionLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = " int(20) comment 'iotDasConnectionLogId' ")
    private Long iotDasConnectionLogId;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '节点id' ")
    private String nodeId;

    @Column(columnDefinition = " varchar(255) comment '节点ip' ")
    private String nodeIp;

    @Column(columnDefinition = " varchar(10) comment '节点端口' ")
    private int nodePort;

    @Column(columnDefinition = " char(1) comment '是否连接' ")
    @Type(type = "yes_no")
    private boolean connected;

    @Column(columnDefinition = " datetime comment '数据创建时间'")
    private Date inputDate = new Date();

    @Column(columnDefinition = " datetime comment '记录修改时间' ")
    private Date updateDate = new Date();

    @Column(columnDefinition = " timestamp not null COMMENT '大数据所需要的日期字段,记录更新时间' ")
    private Date stampDate;

    @Column(columnDefinition = " char(1) comment '是否逻辑删除' ")
    @Type(type = "yes_no")
    private boolean isDelete;

    public Long getIotDasConnectionLogId() {
        return iotDasConnectionLogId;
    }

    public void setIotDasConnectionLogId(Long iotDasConnectionLogId) {
        this.iotDasConnectionLogId = iotDasConnectionLogId;
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
}
