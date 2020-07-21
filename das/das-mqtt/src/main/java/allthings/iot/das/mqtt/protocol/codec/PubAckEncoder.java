package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.PubAckMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

class PubAckEncoder extends AbstractDemuxEncoder<PubAckMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, PubAckMessage msg, ByteBuf out) {
        ByteBuf buff = chc.alloc().buffer(4);
        try {
            buff.writeByte(AbstractMessage.PUBACK << 4);
            buff.writeBytes(CodecUtil.encodeRemainingLength(2));
            buff.writeShort(msg.getMessageID());
            out.writeBytes(buff);
        } finally {
            buff.release();
        }
    }

}
