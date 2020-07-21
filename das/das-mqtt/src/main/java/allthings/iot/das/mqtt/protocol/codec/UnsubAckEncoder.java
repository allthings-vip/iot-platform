package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.UnsubAckMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

class UnsubAckEncoder extends AbstractDemuxEncoder<UnsubAckMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, UnsubAckMessage msg, ByteBuf out) {
        out.writeByte(AbstractMessage.UNSUBACK << 4).
                writeBytes(CodecUtil.encodeRemainingLength(2)).
                writeShort(msg.getMessageID());
    }
}