package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PublishMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.AttributeMap;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

import java.util.List;

class PublishDecoder extends AbstractDemuxDecoder {

    private static final String PLUS = "+";
    private static final String SHARP = "#";

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        in.resetReaderIndex();
        int startPos = in.readerIndex();

        //Common decoding part
        PublishMessage message = new PublishMessage();
        if (!decodeCommonHeader(message, in)) {
            in.resetReaderIndex();
            return;
        }

        if (CodecUtil.isMQTT311(ctx)) {
            if (message.getQos() == AbstractMessage.QOSType.MOST_ONE && message.isDupFlag()) {
                //bad protocol, if QoS=0 => DUP = 0
                throw new CorruptedFrameException("Received a PUBLISH with QoS=0 & DUP = 1, MQTT 3.1.1 violation");
            }

            if (message.getQos() == AbstractMessage.QOSType.RESERVED) {
                throw new CorruptedFrameException("Received a PUBLISH with QoS flags setted 10 b11, MQTT 3.1.1 " +
                        "violation");
            }
        }

        int remainingLength = message.getRemainingLength();

        //Topic name
        String topic = CodecUtil.decodeString(in);
        if (topic == null) {
            in.resetReaderIndex();
            return;
        }
        if (topic.contains(PLUS) || topic.contains(SHARP)) {
            throw new CorruptedFrameException("Received a PUBLISH with topic containting wild card chars, topic: " +
                    topic);
        }

        message.setTopicName(topic);

        if (message.getQos() == AbstractMessage.QOSType.LEAST_ONE ||
                message.getQos() == AbstractMessage.QOSType.EXACTLY_ONCE) {
            message.setMessageID(in.readUnsignedShort());
        }
        int stopPos = in.readerIndex();

        //read the payload
        int payloadSize = remainingLength - (stopPos - startPos - 2) + (CodecUtil.numBytesToEncode(remainingLength) -
                1);
        if (in.readableBytes() < payloadSize) {
            in.resetReaderIndex();
            return;
        }
//        byte[] b = new byte[payloadSize];
        ByteBuf bb = Unpooled.buffer(payloadSize);
        try {
            in.readBytes(bb);
            message.setPayload(bb.nioBuffer());
            out.add(message);
        } finally {
            bb.release();
        }
    }

}
