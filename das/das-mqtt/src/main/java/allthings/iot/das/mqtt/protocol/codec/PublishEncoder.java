package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.PublishMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

class PublishEncoder extends AbstractDemuxEncoder<PublishMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PublishMessage message, ByteBuf out) {
        if (message.getQos() == AbstractMessage.QOSType.RESERVED) {
            throw new IllegalArgumentException("Found a message with RESERVED Qos");
        }
        if (message.getTopicName() == null || message.getTopicName().isEmpty()) {
            throw new IllegalArgumentException("Found a message with empty or null topic name");
        }

        ByteBuf variableHeaderBuff = ctx.alloc().buffer(2);
        ByteBuf buff = null;
        try {
            variableHeaderBuff.writeBytes(CodecUtil.encodeString(message.getTopicName()));
            if (message.getQos() == AbstractMessage.QOSType.LEAST_ONE ||
                    message.getQos() == AbstractMessage.QOSType.EXACTLY_ONCE) {
                if (message.getMessageID() == null) {
                    throw new IllegalArgumentException("Found a message with QOS 1 or 2 and not MessageID setted");
                }
                variableHeaderBuff.writeShort(message.getMessageID());
            }
            variableHeaderBuff.writeBytes(message.getPayload().duplicate());
            int variableHeaderSize = variableHeaderBuff.readableBytes();

            byte flags = CodecUtil.encodeFlags(message);

            buff = ctx.alloc().buffer(2 + variableHeaderSize);
            buff.writeByte(AbstractMessage.PUBLISH << 4 | flags);
            buff.writeBytes(CodecUtil.encodeRemainingLength(variableHeaderSize));
            buff.writeBytes(variableHeaderBuff);
            out.writeBytes(buff);
        } finally {
            variableHeaderBuff.release();
            if (buff != null) {
                buff.release();
            }
        }
    }

}
