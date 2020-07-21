package allthings.iot.dms.dto;

/**
 * @author :  sylar
 * @FileName :  MsgLogDto
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
public class MsgLogDto extends AbstractDeviceDto {

    private Long iotMsgLogId;

    private String msgType;

    private String msgContent;

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
