package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PubAckMessage;
import allthings.iot.das.mqtt.protocol.message.MessageIDMessage;


class PubAckDecoder extends AbstractMessageIDDecoder {

    @Override
    protected MessageIDMessage createMessage() {
        return new PubAckMessage();
    }

}
