package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PingRespMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeMap;

import java.util.List;

class PingRespDecoder extends AbstractDemuxDecoder {

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        //Common decoding part
        in.resetReaderIndex();
        PingRespMessage message = new PingRespMessage();
        int expectedFlags = 0x00;
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }
        out.add(message);
    }
}
