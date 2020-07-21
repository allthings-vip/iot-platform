package allthings.iot.das.common.core;

import com.lmax.disruptor.EventHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.msg.MsgType;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.common.bean.ChannelCache;
import allthings.iot.das.common.bean.MsgThrower;
import allthings.iot.das.common.event.ChannelMsgEvent;
import allthings.iot.util.disruptor.IMessaging;
import allthings.iot.util.disruptor.LmaxDiscuptorMessaging;
import allthings.iot.util.disruptor.ValueEvent;

import javax.annotation.PostConstruct;

/**
 * @author :  sylar
 * @FileName :  InternalInboundMsgProcessor
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class InternalInboundMsgProcessor implements ApplicationListener<ChannelMsgEvent>, EventHandler<ValueEvent> {
    private final static Logger LOG = LoggerFactory.getLogger(InternalInboundMsgProcessor.class);

    @Autowired
    DasConfig dasConfig;

    @Autowired
    private ChannelCache channelCache;

    @Autowired
    private MsgThrower msgThrower;

    @Autowired
    private InboundMsgProcessor inboundMsgProcessor;

    private IMessaging messaging;

    @PostConstruct
    private void init() {
        messaging = new LmaxDiscuptorMessaging(this);
    }

    @Override
    public void onApplicationEvent(ChannelMsgEvent event) {
        messaging.publish(event);
    }

    @Override
    public void onEvent(ValueEvent valueEvent, long sequence, boolean endOfBatch) throws Exception {
        Object obj = valueEvent.getValue();
        if (obj instanceof ChannelMsgEvent) {
            ChannelMsgEvent cmEvent = (ChannelMsgEvent) obj;

            Channel channel = cmEvent.getChannel();
            IMsg msg = cmEvent.getMsg();

            boolean needThrowUp = true;
            MsgType msgType = msg.getMsgType();

            if (NettyUtil.isUdpChannel(channel)) {
                String clientId = msg.getPlatformCode() == null ? (msg.getSourceDeviceType() + msg.getSourceDeviceId()) : msg.getPlatformCode();
                channelCache.put(clientId, channel);
            }

            switch (msgType) {
                case DeviceConnection:
                    needThrowUp = onDeviceConnection(channel, (DeviceConnectionMsg) msg);
                    break;
                default:
                    break;
            }

            if (inboundMsgProcessor != null) {
                needThrowUp = inboundMsgProcessor.processInboundMsg(msg);
            }

            if (needThrowUp) {
                msgThrower.sendToQueue(msg);
            }
        }
    }

    private boolean onDeviceConnection(Channel channel, DeviceConnectionMsg msg) {
        String clientId = msg.getPlatformCode() == null ? msg.getSourceDeviceId() : msg.getPlatformCode();
        if (msg.isConnected()) {
            //当建立新连接时,为channel加上ClientId属性标记,并将channel加入本地缓存
            NettyUtil.setDeviceType(channel, msg.getSourceDeviceType());
            NettyUtil.setClientId(channel, clientId);
            channelCache.put(clientId, channel);
            LOG.debug("onDeviceConnection connected:{}", msg.getSourceDeviceId());
        } else {
            if (channelCache.get(clientId) == channel) {
                //当连接断开时,从本地缓存里清除
                channelCache.remove(clientId);
                LOG.debug("onDeviceConnection disconnected:{}", msg.getSourceDeviceId());
            }
        }

        return true;
    }

}
