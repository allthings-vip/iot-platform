package allthings.iot.das.mqtt.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeMap;
import allthings.iot.das.mqtt.protocol.message.MessageIDMessage;

import java.util.List;

abstract class AbstractMessageIDDecoder extends AbstractDemuxDecoder {

    /**
     * 创建消息内容
     *
     * @return MessageIDMessage
     */
    protected abstract MessageIDMessage createMessage();

    @Override
    void decode(AttributeMap ctx, ByteBuf in, List<Object> out) throws Exception {
        in.resetReaderIndex();
        //Common decoding part
        int expectedFlags = 0x00;
        MessageIDMessage message = createMessage();
        if (!decodeCommonHeader(message, expectedFlags, in)) {
            in.resetReaderIndex();
            return;
        }

        //read  messageIDs
        message.setMessageID(in.readUnsignedShort());
        out.add(message);
    }

}
