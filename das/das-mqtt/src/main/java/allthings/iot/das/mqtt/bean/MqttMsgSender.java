package allthings.iot.das.mqtt.bean;

import allthings.iot.das.mqtt.protocol.message.PublishMessage;
import allthings.iot.das.mqtt.protocol.subscriptions.Subscription;
import io.netty.channel.Channel;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.common.bean.ChannelCache;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :  sylar
 * @FileName :  MqttMsgSender
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class MqttMsgSender {

    private final Logger LOG = LoggerFactory.getLogger(MqttMsgSender.class);

    @Autowired
    ChannelCache channelCache;

    @Autowired
    MqttTopicCache topicSubscriptionCache;

    public void send(PublishMessage message) {
        AbstractMessage.QOSType qos = message.getQos();
        if (AbstractMessage.QOSType.MOST_ONE == qos) {
            publish2Subscribers(message);
        } else if (AbstractMessage.QOSType.LEAST_ONE == qos) {
            //TODO
        } else if (AbstractMessage.QOSType.EXACTLY_ONCE == qos) {
            //TODO
        }
    }

    private void publish2Subscribers(PublishMessage message) {
        String topic = message.getTopicName();
        AbstractMessage.QOSType orignQos = message.getQos();
        for (final Subscription sub : topicSubscriptionCache.getSubscriptions(topic).values()) {
            if (orignQos.ordinal() > sub.getRequestedQos().ordinal()) {
                message.setQos(sub.getRequestedQos());
            }
            String clientId = sub.getClientId();
            AbstractMessage.QOSType qos = message.getQos();
            if (AbstractMessage.QOSType.MOST_ONE == qos && sub.isActive()) {
                sendPublishMessage(clientId, message);
            } else if (AbstractMessage.QOSType.LEAST_ONE == qos) {
                //TODO
            } else if (AbstractMessage.QOSType.EXACTLY_ONCE == qos) {
                //TODO
            }
            LOG.debug("publish to subscriber for clientId:{}, topic:{}, qos:{}, active:{}",
                    clientId, sub.getTopicFilter(), qos, sub.isActive());
        }
    }

    private void sendPublishMessage(String clientId, PublishMessage message) {
        Channel channel = channelCache.get(clientId);
        if (channel == null) {
            LOG.warn("send publish message can't find channel for clientId: {}", clientId);
            return;
        }
        NettyUtil.writeData(channel, message);
        LOG.debug("send publish message for clientId: {} on message: {}", clientId, message);
    }

}
