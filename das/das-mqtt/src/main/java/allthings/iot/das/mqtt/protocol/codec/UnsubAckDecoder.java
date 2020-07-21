package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.MessageIDMessage;
import allthings.iot.das.mqtt.protocol.message.UnsubAckMessage;

class UnsubAckDecoder extends AbstractMessageIDDecoder {

    @Override
    protected MessageIDMessage createMessage() {
        return new UnsubAckMessage();
    }
}

