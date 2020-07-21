/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.util.misc.ByteUtils;

/**
 * 类MsgHeader.java的实现描述：TODO 类实现描述
 *
 * @author mingyuan.miao 2018年3月20日 上午10:29:43
 */
public class MsgHeader implements UnPackable {

    private int msgLength;
    private int msgSn;
    private String msgId;
    private int msgGnssCenterId;
    private byte[] versionFlag = new byte[3];
    private byte encryptFlag;
    private int encryptKey;

    private byte[] headerBytes;

    @Override
    public void unPack(byte[] bytes) {
        this.headerBytes = bytes;
        ByteBuf headerBuf = Unpooled.wrappedBuffer(bytes);
        msgLength = headerBuf.readInt();
        msgSn = headerBuf.readInt();

        byte[] msgIdBytes = new byte[2];
        headerBuf.readBytes(msgIdBytes);
        msgId = ByteUtils.bytesToHexString(msgIdBytes);

        msgGnssCenterId = headerBuf.readInt();
        headerBuf.readBytes(versionFlag);
        encryptFlag = headerBuf.readByte();
        encryptKey = headerBuf.readInt();
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getMsgSn() {
        return msgSn;
    }

    public void setMsgSn(int msgSn) {
        this.msgSn = msgSn;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public void setHeaderBytes(byte[] headerBytes) {
        this.headerBytes = headerBytes;
    }

    public int getMsgGnssCenterId() {
        return msgGnssCenterId;
    }

    public void setMsgGnssCenterId(int msgGnssCenterId) {
        this.msgGnssCenterId = msgGnssCenterId;
    }

    public byte[] getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }

    public byte getEncryptFlag() {
        return encryptFlag;
    }

    public void setEncryptFlag(byte encryptFlag) {
        this.encryptFlag = encryptFlag;
    }

    public int getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(int encryptKey) {
        this.encryptKey = encryptKey;
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msgLength", msgLength);
        map.put("msgSn", msgSn);
        map.put("msgId", msgId);
        map.put("msgGnssCenterId", msgGnssCenterId);
        String version = versionFlag[0] + "." + versionFlag[1] + "." + versionFlag[2];
        map.put("versionFlag", version);
        map.put("encryptFlag", encryptFlag);
        map.put("encryptKey", encryptKey);
        return map;
    }

    @Override
    public String toString() {
        return "MsgHeader [msgLength=" + msgLength + ", msgSn=" + msgSn + ", msgId=" + msgId + ", msgGnssCenterId="
                + msgGnssCenterId + ", versionFlag=" + Arrays.toString(versionFlag) + ", encryptFlag=" + encryptFlag
                + ", encryptKey=" + encryptKey + "]";
    }

}
