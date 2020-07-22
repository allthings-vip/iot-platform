package allthings.iot.das.mqtt.event;

import io.netty.channel.Channel;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

/**
 * @author :  sylar
 * @FileName :  MqttProtocolEvent
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class MqttProtocolEvent extends MqttEvent {
    private AbstractMessage message;

    public MqttProtocolEvent(Object source, Channel channel, AbstractMessage message) {
        super(source, channel);
        this.message = message;
    }

    public AbstractMessage getMessage() {
        return message;
    }
}
