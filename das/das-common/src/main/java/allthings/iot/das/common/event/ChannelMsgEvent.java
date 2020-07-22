package allthings.iot.das.common.event;

import io.netty.channel.Channel;
import allthings.iot.common.msg.IMsg;
import org.springframework.context.ApplicationEvent;

/**
 * @author :  sylar
 * @FileName :  ChannelMsgEvent
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
public class ChannelMsgEvent extends ApplicationEvent {
    private Channel channel;
    private IMsg msg;

    public ChannelMsgEvent(Object source, Channel channel, IMsg msg) {
        super(source);
        this.channel = channel;
        this.msg = msg;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public IMsg getMsg() {
        return msg;
    }

    public void setMsg(IMsg msg) {
        this.msg = msg;
    }
}
