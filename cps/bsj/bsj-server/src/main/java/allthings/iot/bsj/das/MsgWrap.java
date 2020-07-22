package allthings.iot.bsj.das;

import java.nio.ByteBuffer;

/**
 * @author :  luhao
 * @FileName :  MsgWrap
 * @CreateDate :  2017/12/29
 * @Description : 帧解码之后封装的消息，还未对内容进行解码，需要根据主信令对内容进行解码
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class MsgWrap {
    /**
     * 主信令，指令
     */
    private String msgCode;
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 子指令码
     */
    private byte childMsgCode;

    /**
     * crc校验码
     */
    private byte crc;

    /**
     * 内容
     */
    private ByteBuffer content;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public byte getChildMsgCode() {
        return childMsgCode;
    }

    public void setChildMsgCode(byte childMsgCode) {
        this.childMsgCode = childMsgCode;
    }

    public byte getCrc() {
        return crc;
    }

    public void setCrc(byte crc) {
        this.crc = crc;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public ByteBuffer getContent() {
        return content;
    }

    public void setContent(ByteBuffer content) {
        this.content = content;
    }
}
