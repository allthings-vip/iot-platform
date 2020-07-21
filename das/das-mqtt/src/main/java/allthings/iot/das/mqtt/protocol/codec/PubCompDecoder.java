package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PubCompMessage;
import allthings.iot.das.mqtt.protocol.message.MessageIDMessage;


class PubCompDecoder extends AbstractMessageIDDecoder {

    @Override
    protected MessageIDMessage createMessage() {
        return new PubCompMessage();
    }
}
