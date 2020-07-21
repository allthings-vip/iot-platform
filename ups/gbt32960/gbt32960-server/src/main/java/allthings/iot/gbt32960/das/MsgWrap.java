package allthings.iot.gbt32960.das;

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
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
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
     * 响应吗
     */
    private byte responseCode;

    /**
     * crc校验码
     */
    private byte crc;
    /**
     * 加密方式
     */
    private byte encryptType;

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

    public byte getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(byte responseCode) {
        this.responseCode = responseCode;
    }

    public byte getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(byte encryptType) {
        this.encryptType = encryptType;
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
