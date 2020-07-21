package allthings.iot.das.mqtt.core;

import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.common.bean.ChannelCache;
import allthings.iot.das.mqtt.MqttCacheKeys;
import allthings.iot.das.mqtt.MqttConst;
import allthings.iot.das.mqtt.bean.MqttAuthenticator;
import allthings.iot.das.mqtt.bean.MqttMsgSender;
import allthings.iot.das.mqtt.bean.MqttTopicCache;
import allthings.iot.das.mqtt.event.MqttConnectionLostEvent;
import allthings.iot.das.mqtt.event.MqttEvent;
import allthings.iot.das.mqtt.event.MqttProtocolEvent;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.ConnAckMessage;
import allthings.iot.das.mqtt.protocol.message.ConnectMessage;
import allthings.iot.das.mqtt.protocol.message.DisconnectMessage;
import allthings.iot.das.mqtt.protocol.message.PingReqMessage;
import allthings.iot.das.mqtt.protocol.message.PingRespMessage;
import allthings.iot.das.mqtt.protocol.message.PubAckMessage;
import allthings.iot.das.mqtt.protocol.message.PubCompMessage;
import allthings.iot.das.mqtt.protocol.message.PubRecMessage;
import allthings.iot.das.mqtt.protocol.message.PubRelMessage;
import allthings.iot.das.mqtt.protocol.message.PublishMessage;
import allthings.iot.das.mqtt.protocol.message.SubAckMessage;
import allthings.iot.das.mqtt.protocol.message.SubscribeMessage;
import allthings.iot.das.mqtt.protocol.message.UnsubAckMessage;
import allthings.iot.das.mqtt.protocol.message.UnsubscribeMessage;
import allthings.iot.das.mqtt.protocol.message.WillMessage;
import allthings.iot.das.mqtt.protocol.subscriptions.Subscription;
import allthings.iot.util.disruptor.IMessaging;
import allthings.iot.util.disruptor.LmaxDiscuptorMessaging;
import allthings.iot.util.disruptor.ValueEvent;
import allthings.iot.util.redis.ICentralCacheService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  MqttEventProcessor
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class MqttEventProcessor implements ApplicationListener<MqttEvent>, EventHandler<ValueEvent> {
    public final static Logger LOG = LoggerFactory.getLogger(MqttEventProcessor.class);

    @Autowired
    DasConfig dasConfig;

    @Autowired
    MqttAuthenticator authenticator;

    @Autowired
    MqttMsgSender mqttMsgSender;

    @Autowired
    ClusterMsgProcessor clusterMsgProcessor;

    @Autowired
    ChannelCache channelCache;

    @Autowired
    MqttTopicCache topicSubscriptionCache;

    private ICentralCacheService cache;

    private Cache<String, WillMessage> willMessageCache = CacheBuilder.newBuilder()
            .maximumSize(1000000L).concurrencyLevel(16).build();

    private IMessaging messaging;

    @PostConstruct
    private void init() {
        messaging = new LmaxDiscuptorMessaging(this);
    }

    @Override
    public void onApplicationEvent(MqttEvent event) {
        messaging.publish(event);
    }

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {

        Object obj = event.getValue();

        if (obj instanceof MqttConnectionLostEvent) {
            MqttConnectionLostEvent lostEvt = (MqttConnectionLostEvent) obj;
            Channel channel = lostEvt.getChannel();
            String clientId = lostEvt.getClientId();
            try {
                processConnectionLost(channel, clientId);
            } catch (Exception e) {
                LOG.error("exception in processConnectionLost for cause: {}", e.getCause());
                e.printStackTrace();
            }
        } else if (obj instanceof MqttProtocolEvent) {
            MqttProtocolEvent protocolEvent = (MqttProtocolEvent) obj;
            Channel channel = protocolEvent.getChannel();
            AbstractMessage message = protocolEvent.getMessage();
            try {
                dispathMessage(channel, message);
            } catch (Exception e) {
                LOG.error("exception in dispathMessage for messageType:{} and cause: {}",
                        message.getMessageType(), e.getCause());
                e.printStackTrace();
            }
        }
    }

    private void dispathMessage(Channel channel, AbstractMessage message) {
        switch (message.getMessageType()) {
            case AbstractMessage.PINGREQ:
                processPingReqMessage(channel, (PingReqMessage) message);
                break;
            case AbstractMessage.CONNECT:
                processConnectMessage(channel, (ConnectMessage) message);
                break;
            case AbstractMessage.DISCONNECT:
                processDisconnectMessage(channel, (DisconnectMessage) message);
                break;
            case AbstractMessage.SUBSCRIBE:
                processSubscribeMessage(channel, (SubscribeMessage) message);
                break;
            case AbstractMessage.UNSUBSCRIBE:
                processUnsubscribeMessage(channel, (UnsubscribeMessage) message);
                break;
            case AbstractMessage.PUBACK:
                processPubAckMessage(channel, (PubAckMessage) message);
                break;
            case AbstractMessage.PUBREL:
                processPubRelMessage(channel, (PubRelMessage) message);
                break;
            case AbstractMessage.PUBREC:
                processPubRecMessage(channel, (PubRecMessage) message);
                break;
            case AbstractMessage.PUBCOMP:
                processPubCompMessage(channel, (PubCompMessage) message);
                break;
            case AbstractMessage.PUBLISH:
                processPublishMessage(channel, (PublishMessage) message);
                break;
            default:
                break;
        }

    }

    private void processPublishMessage(Channel channel, PublishMessage message) {
        String clientId = NettyUtil.getClientId(channel);
        if (Strings.isNullOrEmpty(clientId)) {
            NettyUtil.closeChannel(channel, true);
            LOG.warn("receive publish message before connect message for channel:{}", channel);
            return;
        }

        LOG.debug("process publish message: {} for clientId: {}", message, clientId);

        //转发给本地DAS上的其它设备或app
        mqttMsgSender.send(message);

        //转发给其他DAS服务器处理
        clusterMsgProcessor.processMsg(message);
    }

    private void processConnectionLost(Channel channel, String clientId) {
        Channel cacheChannel = channelCache.get(clientId);
        if (cacheChannel == null) {
            LOG.warn("lost connection with clientId: {} which channel not exist in channelCache", clientId);
            return;
        }
        if (cacheChannel.equals(channel)) {
            channelCache.remove(clientId);
            boolean cleanSession = NettyUtil.isCleanSession(channel);
            if (cleanSession) {
                cleanSession(clientId);
            }
            //publish the Will message (if any) for the clientId
            WillMessage willMessage = willMessageCache.getIfPresent(clientId);
            if (willMessage != null) {
                mqttMsgSender.send(willMessage);
                willMessageCache.invalidate(clientId);
                LOG.debug("process will message: {} for clientId: {}", willMessage, clientId);
            }
            LOG.info("lost connection with clientId: {}", clientId);
        } else {
            LOG.debug("lost connection with clientId: {} which has already reconnected", clientId);
        }
    }

    private void processPingReqMessage(Channel channel, PingReqMessage message) {
        PingRespMessage pingResp = new PingRespMessage();
        channel.writeAndFlush(pingResp);
    }

    private void processConnectMessage(Channel channel, ConnectMessage message) {
        String clientId = message.getClientID();
        if (!checkConnectValid(channel, message)) {
            return;
        }
        Channel existOldChannel = channelCache.get(clientId);
        //put clientId and channel to cache
        channelCache.put(clientId, channel);
        //if an old client with the same ID already exists
        if (existOldChannel != null) {
            NettyUtil.closeChannel(existOldChannel, true);
            LOG.info("close an existing connection with same clientId:{}", clientId);
        }
        //handle will flag
        if (message.isWillFlag()) {
            WillMessage will = new WillMessage(message.getWillTopic(), ByteBuffer.wrap(message.getWillMessage()
                    .getBytes(Charsets.UTF_8)),
                    message.isWillRetain(), AbstractMessage.QOSType.values()[message.getWillQos()]);
            willMessageCache.put(clientId, will);
            LOG.debug("save will message for clientId: {}", clientId);
        }
        //handle clean session flag
        boolean cleanSession = message.isCleanSession();
        if (cleanSession) {
            cleanSession(clientId);
        }
        //handle keepAlive
        int keepAlive = message.getKeepAlive();
        NettyUtil.setChannelIdleTime(channel, Math.round(keepAlive * 2.5f));
        //set attributes
        NettyUtil.setKeepAlive(channel, keepAlive);
        NettyUtil.setCleanSession(channel, cleanSession);
        NettyUtil.setClientId(channel, clientId);
        //write ok resp
        ConnAckMessage okResp = new ConnAckMessage();
        okResp.setReturnCode(ConnAckMessage.CONNECTION_ACCEPTED);
        NettyUtil.writeData(channel, okResp);
        LOG.info("channle connected for clientId:{} with protocolVersion:{} and keepAlive:{} and cleanSession:{}",
                clientId, message.getProcotolVersion(), keepAlive, cleanSession);
    }

    private void processDisconnectMessage(Channel channel, DisconnectMessage message) {
        String clientId = NettyUtil.getClientId(channel);
        if (Strings.isNullOrEmpty(clientId)) {
            NettyUtil.closeChannel(channel, true);
            LOG.warn("receive disconnect message before connect message for channel:{}", channel);
            return;
        }
        willMessageCache.invalidate(clientId);
        NettyUtil.closeChannel(channel, true);
        LOG.info("process disconnect message for client: {} ", clientId);
    }

    private void processSubscribeMessage(Channel channel, SubscribeMessage message) {
        String clientId = NettyUtil.getClientId(channel);
        if (Strings.isNullOrEmpty(clientId)) {
            NettyUtil.closeChannel(channel, true);
            LOG.warn("receive subscribe message before connect message for channel:{}", channel);
            return;
        }
        boolean cleanSession = NettyUtil.isCleanSession(channel);
        List<Subscription> subscriptions = Lists.newArrayList();
        for (SubscribeMessage.Couple req : message.subscriptions()) {
            AbstractMessage.QOSType qos = AbstractMessage.QOSType.values()[req.getQos()];
            Subscription newSubscription = new Subscription(clientId, req.getTopicFilter(), qos, cleanSession);
            subscriptions.add(newSubscription);
        }
        //添加本地订阅信息并向中心注册新的topic
        addSubscriptionsToLocalAndCCS(subscriptions);
        //ack the client
        SubAckMessage ackMessage = new SubAckMessage();
        ackMessage.setMessageID(message.getMessageID());
        //reply with requested qos
        for (SubscribeMessage.Couple req : message.subscriptions()) {
            AbstractMessage.QOSType qos = AbstractMessage.QOSType.values()[req.getQos()];
            ackMessage.addType(qos);
        }
        NettyUtil.writeData(channel, ackMessage);
        LOG.debug("process subscribe message from clientId: {} with messageId: {}", clientId, message.getMessageID());
    }

    private void processUnsubscribeMessage(Channel channel, UnsubscribeMessage message) {
        String clientId = NettyUtil.getClientId(channel);
        if (Strings.isNullOrEmpty(clientId)) {
            NettyUtil.closeChannel(channel, true);
            LOG.warn("receive unsubscribe message before connect message for channel:{}", channel);
            return;
        }
        //remove subscriptions
        removeSubscriptionsFromLocalAndCCS(clientId, message.topicFilters());
        //write back
        UnsubAckMessage ackMessage = new UnsubAckMessage();
        ackMessage.setMessageID(message.getMessageID());
        NettyUtil.writeData(channel, ackMessage);
        LOG.debug("process unsubscribe message removing subscription on topics: {}, for clientId: {}", message
                .topicFilters(), clientId);
    }

    private void processPubAckMessage(Channel channel, PubAckMessage message) {
        //TODO
    }

    private void processPubRelMessage(Channel channel, PubRelMessage message) {
        //TODO
    }

    private void processPubCompMessage(Channel session, PubCompMessage message) {
        //TODO
    }

    private void processPubRecMessage(Channel channel, PubRecMessage message) {
        String clientId = NettyUtil.getClientId(channel);
        if (Strings.isNullOrEmpty(clientId)) {
            NettyUtil.closeChannel(channel, true);
            LOG.warn("receive pubrec message before connect message for channel:{}", channel);
            return;
        }
        int messageId = message.getMessageID();
        PubRelMessage pubRelMessage = new PubRelMessage();
        pubRelMessage.setMessageID(messageId);
        pubRelMessage.setQos(AbstractMessage.QOSType.LEAST_ONE);
        NettyUtil.writeData(channel, pubRelMessage);
        LOG.debug("send pubrec message for clientId: {} ad messageId: {}", clientId, messageId);
    }

    /**
     * =====================================================================================
     */

    private synchronized void addSubscriptionsToLocalAndCCS(List<Subscription> subscriptions) {
        List<String> newTopicList = topicSubscriptionCache.addSubscriptions(subscriptions);
        for (String newTopic : newTopicList) {
            String topicKey = MqttCacheKeys.getCcsKeyForNodesByTopic(newTopic);
            cache.putObjectToSet(topicKey,
                    String.valueOf(dasConfig.getDasNodeId()));
        }
    }

    private synchronized void removeSubscriptionsFromLocalAndCCS(String clientId, List<String> topicList) {
        List<String> needRemoveTopicList = topicSubscriptionCache.removeSubscriptions(clientId, topicList);
        for (String needRemoveTopic : needRemoveTopicList) {
            String topicKey = MqttCacheKeys.getCcsKeyForNodesByTopic(needRemoveTopic);
            cache.removeObjectFromSet(topicKey,
                    String.valueOf(dasConfig.getDasNodeId()));
        }
    }

    private boolean checkConnectValid(Channel channel, ConnectMessage message) {
        String clientId = message.getClientID();
        int procotolVersion = message.getProcotolVersion();
        //check procotolVersion
        if (procotolVersion != MqttConst.VERSION_3_1 && procotolVersion != MqttConst.VERSION_3_1_1) {
            ConnAckMessage badProto = new ConnAckMessage();
            badProto.setReturnCode(ConnAckMessage.UNNACEPTABLE_PROTOCOL_VERSION);
            NettyUtil.writeDataThenClose(channel, badProto);
            LOG.warn("close channel for connect message with wrong protocol version:{} for clientId:{}",
                    procotolVersion, clientId);
            return false;
        }
        //check clientId
        if (Strings.isNullOrEmpty(clientId)) {
            ConnAckMessage badId = new ConnAckMessage();
            badId.setReturnCode(ConnAckMessage.IDENTIFIER_REJECTED);
            NettyUtil.writeDataThenClose(channel, badId);
            LOG.warn("close channel for connect message with null or empty clientId");
            return false;
        }
        //check userName and password
        if (!message.isUserFlag() || !message.isPasswordFlag() || !authenticator.checkValid(message.getUsername(),
                message.getPassword())) {
            ConnAckMessage badAccount = new ConnAckMessage();
            badAccount.setReturnCode(ConnAckMessage.BAD_USERNAME_OR_PASSWORD);
            NettyUtil.writeDataThenClose(channel, badAccount);
            LOG.warn("close channel for connect message with wrong userName:{} or password:{}", message.getUsername()
                    , message.getPassword());
            return false;
        }
        return true;
    }

    private synchronized void cleanSession(String clientId) {
        List<String> needRemoveTopicList = topicSubscriptionCache.removeForClientId(clientId);
        for (String needRemoveTopic : needRemoveTopicList) {
            String topicKey = MqttCacheKeys.getCcsKeyForNodesByTopic(needRemoveTopic);
            cache.removeObjectFromSet(topicKey,
                    String.valueOf(dasConfig.getDasNodeId()));
        }
        LOG.debug("clean saved subscriptions for clientId:{}", clientId);
    }

//    long getWillMessageCacheSize() {
//        return willMessageCache.size();
//    }
//
//    long getMessagingRemainBufferSize() {
//        return messagingService.getRemainBufferSize();
//    }

}
