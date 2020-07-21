package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PubCompMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

class PubCompEncoder extends AbstractDemuxEncoder<PubCompMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, PubCompMessage msg, ByteBuf out) {
        out.writeByte(AbstractMessage.PUBCOMP << 4);
        out.writeBytes(CodecUtil.encodeRemainingLength(2));
        out.writeShort(msg.getMessageID());
    }
}
