package allthings.iot.das.mqtt.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.ConnectMessage;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class ConnectEncoder extends AbstractDemuxEncoder<ConnectMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, ConnectMessage message, ByteBuf out) {
        ByteBuf staticHeaderBuff = chc.alloc().buffer(12);
        ByteBuf buff = chc.alloc().buffer();
        ByteBuf variableHeaderBuff = chc.alloc().buffer(12);
        try {
            staticHeaderBuff.writeBytes(CodecUtil.encodeString("MQIsdp"));

            //version 
            staticHeaderBuff.writeByte(0x03);

            //connection flags and Strings
            byte connectionFlags = 0;
            if (message.isCleanSession()) {
                connectionFlags |= 0x02;
            }
            if (message.isWillFlag()) {
                connectionFlags |= 0x04;
            }
            connectionFlags |= ((message.getWillQos() & 0x03) << 3);
            if (message.isWillRetain()) {
                connectionFlags |= 0x020;
            }
            if (message.isPasswordFlag()) {
                connectionFlags |= 0x040;
            }
            if (message.isUserFlag()) {
                connectionFlags |= 0x080;
            }
            staticHeaderBuff.writeByte(connectionFlags);

            //Keep alive timer
            staticHeaderBuff.writeShort(message.getKeepAlive());

            //Variable part
            if (message.getClientID() != null) {
                variableHeaderBuff.writeBytes(CodecUtil.encodeString(message.getClientID()));
                if (message.isWillFlag()) {
                    variableHeaderBuff.writeBytes(CodecUtil.encodeString(message.getWillTopic()));
                    variableHeaderBuff.writeBytes(CodecUtil.encodeString(message.getWillMessage()));
                }
                if (message.isUserFlag() && message.getUsername() != null) {
                    variableHeaderBuff.writeBytes(CodecUtil.encodeString(message.getUsername()));
                    if (message.isPasswordFlag() && message.getPassword() != null) {
                        variableHeaderBuff.writeBytes(CodecUtil.encodeString(message.getPassword()));
                    }
                }
            }

            int variableHeaderSize = variableHeaderBuff.readableBytes();
            buff.writeByte(AbstractMessage.CONNECT << 4);
            buff.writeBytes(CodecUtil.encodeRemainingLength(12 + variableHeaderSize));
            buff.writeBytes(staticHeaderBuff).writeBytes(variableHeaderBuff);

            out.writeBytes(buff);
        } finally {
            staticHeaderBuff.release();
            buff.release();
            variableHeaderBuff.release();
        }
    }

}
