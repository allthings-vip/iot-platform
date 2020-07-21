package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PingReqMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeMap;

import java.util.List;

class PingReqDecoder extends AbstractDemuxDecoder {

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        //Common decoding part
        in.resetReaderIndex();
        PingReqMessage message = new PingReqMessage();
        int expectedFlags = 0x00;
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }
        out.add(message);
    }
}