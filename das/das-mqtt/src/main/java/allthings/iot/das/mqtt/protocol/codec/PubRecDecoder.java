package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.PubRecMessage;
import allthings.iot.das.mqtt.protocol.message.MessageIDMessage;

class PubRecDecoder extends AbstractMessageIDDecoder {

    @Override
    protected MessageIDMessage createMessage() {
        return new PubRecMessage();
    }
}
