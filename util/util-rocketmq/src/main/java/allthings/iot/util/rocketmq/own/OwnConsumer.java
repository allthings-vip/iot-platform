package allthings.iot.util.rocketmq.own;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageExt;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import allthings.iot.util.rocketmq.AbsConsumer;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IConsumerConfig;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;

import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class OwnConsumer extends AbsConsumer implements IConsumer {

    OwnFactory factory;
    DefaultMQPushConsumer consumer;

    protected OwnConsumer(OwnFactory factory, IConsumerConfig config) {
        super(config);
        this.factory = factory;
        this.consumer = new DefaultMQPushConsumer();
        init();
    }

    void init() {
        consumer.setNamesrvAddr(factory.getNameServerAddr());
        consumer.setConsumerGroup(config.getConsumerId());
        consumer.setVipChannelEnabled(false);
        consumer.setMessageModel(MessageModel.CLUSTERING);
    }

    @Override
    public void subscribe(String topic, String[] tags, IRocketMsgListener listener) {
        unsubscribe();

        String subExpression = "*";
        if (tags != null && tags.length > 0) {
            subExpression = Joiner.on("||").skipNulls().join(tags);
        }

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext
                    consumeConcurrentlyContext) {
                List<RocketMsg> msgList = Lists.newArrayList();
                for (MessageExt ext : list) {
                    String content = new String(ext.getBody(), Charsets.UTF_8);
                    RocketMsg msg = new RocketMsg(ext.getTopic(), content);
                    msg.setTags(ext.getTags());
                    msg.setKeys(ext.getKeys());
                    msg.setExt(ext);
                    msgList.add(msg);
                }

                try {
                    listener.onSuccess(msgList);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    String err = String.format("处理消息发生异常. \n%s", e.getMessage());
                    System.out.println(err);
                    e.printStackTrace();

                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });

        try {
            consumer.subscribe(topic, subExpression);
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe() {
        consumer.shutdown();
    }
}
