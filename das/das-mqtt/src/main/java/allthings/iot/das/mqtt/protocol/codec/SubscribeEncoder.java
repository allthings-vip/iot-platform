package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.SubscribeMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage.QOSType;

class SubscribeEncoder extends AbstractDemuxEncoder<SubscribeMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, SubscribeMessage message, ByteBuf out) {
        if (message.subscriptions().isEmpty()) {
            throw new IllegalArgumentException("Found a subscribe message with empty topics");
        }

        if (message.getQos() != QOSType.LEAST_ONE) {
            throw new IllegalArgumentException("Expected a message with QOS 1, found " + message.getQos());
        }

        ByteBuf variableHeaderBuff = chc.alloc().buffer(4);
        ByteBuf buff = null;
        try {
            variableHeaderBuff.writeShort(message.getMessageID());
            for (SubscribeMessage.Couple c : message.subscriptions()) {
                variableHeaderBuff.writeBytes(CodecUtil.encodeString(c.getTopicFilter()));
                variableHeaderBuff.writeByte(c.getQos());
            }

            int variableHeaderSize = variableHeaderBuff.readableBytes();
            byte flags = CodecUtil.encodeFlags(message);
            buff = chc.alloc().buffer(2 + variableHeaderSize);

            buff.writeByte(AbstractMessage.SUBSCRIBE << 4 | flags);
            buff.writeBytes(CodecUtil.encodeRemainingLength(variableHeaderSize));
            buff.writeBytes(variableHeaderBuff);

            out.writeBytes(buff);
        } finally {
            variableHeaderBuff.release();
            buff.release();
        }
    }

}
