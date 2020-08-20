package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(name = "iot_dos_device_status")
public class IotDeviceStatus extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '设备状态唯一编码' ", name = "iot_dos_device_status_id")
    private Long iotDeviceStatusId;

    @Column(columnDefinition = "int(20) comment '设备唯一编码' ", nullable = false, name = "iot_dos_device_id")
    private Long iotDeviceId;

    @Column(columnDefinition = "varchar(500) not null comment '服务器节点ID' ", nullable = false, name = "node_id")
    private String nodeId;

    @Column(columnDefinition = "varchar(500) not null comment '终端Ip' ", nullable = false, name = "terminal_ip")
    private String terminalIp;

    @Column(columnDefinition = " tinyint(1) not null default 0 COMMENT '删除标识 0=不在线 1=在线'", name = "is_connected")
    private Boolean connected;

    @Column(columnDefinition = " datetime DEFAULT CURRENT_TIMESTAMP comment '最近一次连接时间'", name =
            "latest_connect_datetime")
    private Date latestConnectDatetime;

    @Column(columnDefinition = " datetime comment '最近一次下线时间'", name = "latest_disconnect_datetime")
    private Date latestDisConnectDatetime;

    public Long getIotDeviceStatusId() {
        return iotDeviceStatusId;
    }

    public void setIotDeviceStatusId(Long iotDeviceStatusId) {
        this.iotDeviceStatusId = iotDeviceStatusId;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
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

    public Date getLatestConnectDatetime() {
        return latestConnectDatetime;
    }

    public void setLatestConnectDatetime(Date latestConnectDatetime) {
        this.latestConnectDatetime = latestConnectDatetime;
    }

    public Date getLatestDisConnectDatetime() {
        return latestDisConnectDatetime;
    }

    public void setLatestDisConnectDatetime(Date latestDisConnectDatetime) {
        this.latestDisConnectDatetime = latestDisConnectDatetime;
    }
}
