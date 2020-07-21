package allthings.iot.das.mqtt.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.AttributeMap;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.UnsubscribeMessage;

import java.util.List;

class UnsubscribeDecoder extends AbstractDemuxDecoder {

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        //Common decoding part
        in.resetReaderIndex();
        UnsubscribeMessage message = new UnsubscribeMessage();
        int expectedFlags = 0x02;
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }

        //check qos level
        if (message.getQos() != AbstractMessage.QOSType.LEAST_ONE) {
            throw new CorruptedFrameException("Found an Usubscribe message with qos other than LEAST_ONE, was: " +
                    message.getQos());
        }

        int start = in.readerIndex();
        //read  messageIDs
        message.setMessageID(in.readUnsignedShort());
        int readed = in.readerIndex() - start;
        while (readed < message.getRemainingLength()) {
            message.addTopicFilter(CodecUtil.decodeString(in));
            readed = in.readerIndex() - start;
        }
        if (message.topicFilters().isEmpty()) {
            throw new CorruptedFrameException("unsubscribe MUST have got at least 1 topic");
        }
        out.add(message);
    }

}
