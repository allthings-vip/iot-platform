package allthings.iot.das.mqtt.core;

import allthings.iot.common.usual.GroupConsts;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.mqtt.bean.MqttMsgSender;
import allthings.iot.das.mqtt.protocol.message.PublishMessage;
import allthings.iot.util.disruptor.IMessaging;
import allthings.iot.util.disruptor.LmaxDiscuptorMessaging;
import allthings.iot.util.disruptor.ValueEvent;
import allthings.iot.util.redis.ICentralCacheService;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IConsumerConfig;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;


/**
 * @author :  sylar
 * @FileName :  ClusterMsgProcessor
 * @CreateDate :  2017/11/08
 * @Description :  1\通过SPS订阅集群中其它DAS实例发本的当前节点所关注的topic消息
 * 2\通过SPS发布集群中其它DAS实例所关注的topic消息
 * <p>
 * 问题:
 * 1\json 不能反序列化成具体类型的 DeviceMsg
 * 2\非mqtt协议(不支持topic),无法做消息路由
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
public class ClusterMsgProcessor implements EventHandler<ValueEvent> {
    @Autowired
    MqttMsgSender mqttMsgSender;

    @Autowired
    private DasConfig dasConfig;

    private IMessaging messaging;

    private IFactory factory;

    private IConsumer consumer;

    private IProducer producer;

    @Autowired
    private ICentralCacheService cache;

    @PostConstruct
    private void init() {
        messaging = new LmaxDiscuptorMessaging(this);
        factory = dasConfig.getFactory();

        consumer = factory.createConsumer(new IConsumerConfig() {
            @Override
            public String getConsumerId() {
                return Joiner.on("-").join(new String[]{GroupConsts.IOT_DMS_TO_DAS_GROUP, ClusterMsgProcessor.class
                        .getSimpleName()});
            }
        });

        producer = factory.createProducer(new IProducerConfig() {
            @Override
            public String getProducerId() {
                return Joiner.on("-").join(new String[]{GroupConsts.IOT_DMS_TO_DAS_GROUP, ClusterMsgProcessor.class
                        .getSimpleName()});
            }
        });

        consumer.subscribe(TopicConsts.DMS_TO_DAS, new String[]{dasConfig.getDasNodeId()}, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> messages) throws Exception {
                for (RocketMsg rocketMsg : messages) {
                    PublishMessage publishMessage = JSON.parseObject(rocketMsg.getContent(), PublishMessage.class);
                    mqttMsgSender.send(publishMessage);
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void processMsg(PublishMessage message) {
        messaging.publish(message);
    }

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch)
            throws Exception {
        Object object = event.getValue();
        if (object instanceof PublishMessage) {
            PublishMessage msg = (PublishMessage) object;
            String topic = msg.getTopicName();
            //从CCS中查找订阅了这个topic的所有 das nodeId
            Set<String> nodeSet = cache.getObjectsFromSet(topic, String.class);
            for (String nodeId : nodeSet) {
                //通过sps将消息发给其它 das node
                RocketMsg rocketMsg = new RocketMsg(TopicConsts.DMS_TO_DAS);
                List<String> tagList = Lists.newArrayList();
                tagList.add(nodeId);
                rocketMsg.setTagList(tagList);
                //TODO
                producer.syncSend(rocketMsg);
            }
        }
    }
}
