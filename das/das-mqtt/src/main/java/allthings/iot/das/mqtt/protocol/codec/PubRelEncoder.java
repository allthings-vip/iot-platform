package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PubRelMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

class PubRelEncoder extends AbstractDemuxEncoder<PubRelMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, PubRelMessage msg, ByteBuf out) {
        out.writeByte(AbstractMessage.PUBREL << 4);
        out.writeBytes(CodecUtil.encodeRemainingLength(2));
        out.writeShort(msg.getMessageID());
    }
}