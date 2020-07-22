package allthings.iot.das.mqtt.event;

import io.netty.channel.Channel;

/**
 * @author :  sylar
 * @FileName :  MqttConnectionLostEvent
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
public class MqttConnectionLostEvent extends MqttEvent {
    private String clientId;

    public MqttConnectionLostEvent(Object source, Channel channel, String clientId) {
        super(source, channel);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

}
