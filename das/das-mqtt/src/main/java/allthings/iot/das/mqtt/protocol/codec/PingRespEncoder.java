package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PingRespMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

class PingRespEncoder extends AbstractDemuxEncoder<PingRespMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, PingRespMessage msg, ByteBuf out) {
        out.writeByte(AbstractMessage.PINGRESP << 4).writeByte(0);
    }
}
