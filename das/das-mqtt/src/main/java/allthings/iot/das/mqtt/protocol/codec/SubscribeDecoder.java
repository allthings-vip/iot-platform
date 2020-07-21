package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.SubscribeMessage;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.AttributeMap;

import java.io.UnsupportedEncodingException;
import java.util.List;

class SubscribeDecoder extends AbstractDemuxDecoder {

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        //Common decoding part
        int expectedFlags = 0x02;
        SubscribeMessage message = new SubscribeMessage();
        in.resetReaderIndex();
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }

        //check qos level
        if (message.getQos() != AbstractMessage.QOSType.LEAST_ONE) {
            throw new CorruptedFrameException("Received Subscribe message with QoS other than LEAST_ONE, was: " +
                    message.getQos());
        }

        int start = in.readerIndex();
        //read  messageIDs
        message.setMessageID(in.readUnsignedShort());
        int readed = in.readerIndex() - start;
        while (readed < message.getRemainingLength()) {
            decodeSubscription(in, message);
            readed = in.readerIndex() - start;
        }

        if (message.subscriptions().isEmpty()) {
            throw new CorruptedFrameException("subscribe MUST have got at least 1 couple topic/QoS");
        }

        out.add(message);
    }

    /**
     * Populate the message with couple of Qos, topic
     */
    private void decodeSubscription(ByteBuf in, SubscribeMessage message) throws UnsupportedEncodingException {
        String topic = CodecUtil.decodeString(in);
        byte qosByte = in.readByte();
        int flag = 0xFC;
        if ((qosByte & flag) > 0) {
            //the first 6 bits is reserved => has to be 0
            throw new CorruptedFrameException("subscribe MUST have QoS byte with reserved buts to 0, found " +
                    Integer.toHexString(qosByte));
        }
        byte qos = (byte) (qosByte & 0x03);
        //TODO check qos id 000000xx
        message.addSubscription(new SubscribeMessage.Couple(qos, topic));
    }

}
