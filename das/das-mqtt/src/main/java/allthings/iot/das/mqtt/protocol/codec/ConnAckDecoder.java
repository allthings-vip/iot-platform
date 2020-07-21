package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.ConnAckMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeMap;

import java.util.List;

class ConnAckDecoder extends AbstractDemuxDecoder {

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        in.resetReaderIndex();
        //Common decoding part
        int expectedFlags = 0x00;
        ConnAckMessage message = new ConnAckMessage();
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }
        //skip reserved byte
        in.skipBytes(1);

        //read  return code
        message.setReturnCode(in.readByte());
        out.add(message);
    }

}
