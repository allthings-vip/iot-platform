package allthings.iot.das.mqtt.event;

import io.netty.channel.Channel;
import org.springframework.context.ApplicationEvent;

/**
 * @author : sylar
 * @FileName :  MqttEvent
 * @CreateDate :  2017/11/08
 * @Description :
 * @Reviewed-By :
 * @Reviewed-On :
 * @Version-History :
 * @Modified-By :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class MqttEvent extends ApplicationEvent {
    private Channel channel;

    public MqttEvent(Object source, Channel channel) {
        super(source);
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

}
