package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.SubAckMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeMap;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage.QOSType;

import java.util.List;

class SubAckDecoder extends AbstractDemuxDecoder {

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        //Common decoding part
        in.resetReaderIndex();
        SubAckMessage message = new SubAckMessage();
        int expectedFlags = 0x00;
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }
        int remainingLength = message.getRemainingLength();

        //MessageID
        message.setMessageID(in.readUnsignedShort());
        remainingLength -= 2;

        //Qos array
        if (in.readableBytes() < remainingLength) {
            in.resetReaderIndex();
            return;
        }
        for (int i = 0; i < remainingLength; i++) {
            byte qos = in.readByte();
            message.addType(QOSType.values()[qos]);
        }

        out.add(message);
    }

}
