package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author :  sylar
 * @FileName :  IotMsgLog
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
@Table(appliesTo = "IotMsgLog", comment = "消息日志表")
public class IotMsgLog extends AbstractDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotMsgLogId' ")
    private Long iotMsgLogId;

    @Column(columnDefinition = " varchar(255) comment '消息类型' ")
    private String msgType;

    @Column(nullable = false, columnDefinition = "text comment '消息内容'")
    private String msgContent;

    @Column(columnDefinition = " varchar(255) comment '节点id'")
    private String nodeId;

    public Long getIotMsgLogId() {
        return iotMsgLogId;
    }

    public void setIotMsgLogId(Long iotMsgLogId) {
        this.iotMsgLogId = iotMsgLogId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
