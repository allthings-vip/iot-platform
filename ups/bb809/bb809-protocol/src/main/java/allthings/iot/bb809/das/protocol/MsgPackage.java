/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 类MsgEntity.java的实现描述：TODO 类实现描述
 * 
 * @author mingyuan.miao 2018年3月20日 下午12:22:38
 */
public abstract class MsgPackage implements UnPackable {

    private byte[]           headFlag             = new byte[HEAD_LEN];
    private MsgHeader        msgHeader            = new MsgHeader();
    private byte[]           crcBytes;
    private int              crcCode;
    private byte[]           endFlag              = new byte[END_LEN];

    private static final int HEAD_LEN             = 1;                 // 报文头长度
    private static final int MSG_LENGTH_LEN       = 4;                 // 消息头，数据长度长度（包括头标识、数据头、数据体和尾标识）
    private static final int MSG_SN_LEN           = 4;                 // 消息头，报文序列号长度
    private static final int MSG_ID_LEN           = 2;                 // 消息头，业务数据类型长度
    private static final int MSG_GNSSCENTERID_LEN = 4;                 // 消息头，下级平台接入码长度
    private static final int VERSION_FLAG_LEN     = 3;                 // 消息头，协议版本号标识长度
    private static final int ENCRYPT_FLAG_LEN     = 1;                 // 消息头，报文加密标识位长度
    private static final int ENCRYPT_KEY_LEN      = 4;                 // 消息头，数据加密密钥长度
    private static final int CRC_LEN              = 2;                 // CRC校验码长度
    private static final int END_LEN              = 1;                 // 报文尾长度

    public MsgPackage(ByteBuffer buffer){
        buffer.order(ByteOrder.BIG_ENDIAN);
        ByteBuf buf = Unpooled.wrappedBuffer(buffer);

        buf.readBytes(headFlag);

        // 校验数据集
        crcBytes = new byte[buf.readableBytes() - (CRC_LEN + END_LEN)];
        buf.getBytes(buf.readerIndex(), crcBytes);

        // 消息头
        byte[] msgHeaderBytes = new byte[MSG_LENGTH_LEN + MSG_SN_LEN + MSG_ID_LEN + MSG_GNSSCENTERID_LEN
                                         + VERSION_FLAG_LEN + ENCRYPT_FLAG_LEN + ENCRYPT_KEY_LEN];
        buf.readBytes(msgHeaderBytes);
        msgHeader.unPack(msgHeaderBytes);

        // 消息体
        byte[] msgBodyBytes = new byte[buf.readableBytes() - (CRC_LEN + END_LEN)];
        buf.readBytes(msgBodyBytes);

        // 消息体解密处理
        if (msgHeader.getEncryptFlag() > 0) {
            // TODO 解密处理
        }
        this.unPack(msgBodyBytes);// 解析数据体

        // 校验位检查
        crcCode = buf.readShort();

        buf.readBytes(endFlag);
    }

    public abstract String getDeviceId();
    public abstract String getMsgCode();
    public abstract Map<String, Object> getMsgBody();

    public byte[] getHeadFlag() {
        return headFlag;
    }

    public void setHeadFlag(byte[] headFlag) {
        this.headFlag = headFlag;
    }

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public byte[] getCrcBytes() {
        return crcBytes;
    }

    public void setCrcBytes(byte[] crcBytes) {
        this.crcBytes = crcBytes;
    }

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public byte[] getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(byte[] endFlag) {
        this.endFlag = endFlag;
    }

}
