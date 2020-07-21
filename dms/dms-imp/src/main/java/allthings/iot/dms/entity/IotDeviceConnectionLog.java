package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author :  sylar
 * @FileName :  IotDeviceConnectionLog
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
@Entity
@Table(appliesTo = "IotDeviceConnectionLog", comment = "设备连接日志表")
public class IotDeviceConnectionLog extends AbstractDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceConnectionLogId' ")
    private Long iotDeviceConnectionLogId;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '节点id'")
    private String nodeId;

    @Column(columnDefinition = " varchar(255) comment '终端ip'")
    private String terminalIp;

    @Column(columnDefinition = " char(1) comment '是否连接'")
    @Type(type = "yes_no")
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
