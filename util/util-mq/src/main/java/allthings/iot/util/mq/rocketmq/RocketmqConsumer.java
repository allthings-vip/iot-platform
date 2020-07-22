package allthings.iot.util.mq.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.google.common.base.Charsets;
import allthings.iot.util.mq.AbstractConsumer;
import allthings.iot.util.mq.Message;
import allthings.iot.util.mq.MessageListener;

import java.util.Enumeration;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  RocketmqConsumer
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
public class RocketmqConsumer extends AbstractConsumer {

    protected DefaultMQPushConsumer consumer;

    @Override
    public void subscribe(List<String> topics, MessageListener messageListener) {
        super.subscribe(topics, messageListener);

        try {
            initConsumer();
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext
                        consumeConcurrentlyContext) {
                    list.forEach(messageExt -> {
                        Message message = new Message(messageExt.getTopic(), new String(messageExt.getBody(),
                                Charsets.UTF_8));
                        message.setTag(messageExt);
                        messageListener.onSuccess(message);
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            String subExpression = "*";
            topics.forEach(topic -> {
                try {
                    consumer.subscribe(topic, subExpression);
                } catch (MQClientException e) {
                    e.printStackTrace();
                    messageListener.onFailure(e.getCause());
                }
            });

            consumer.start();

        } catch (Exception e) {
            e.printStackTrace();
            messageListener.onFailure(e.getCause());
        }
    }

    @Override
    public void unsubscribe() {
        if (consumer != null) {
            Enumeration<String> topics = consumer.getDefaultMQPushConsumerImpl().getRebalanceImpl()
                    .getTopicSubscribeInfoTable().keys();
            while (topics.hasMoreElements()) {
                consumer.unsubscribe(topics.nextElement());
            }
            consumer.shutdown();
        }
    }

    private void initConsumer() {
        consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(brokers);
        consumer.setConsumerGroup(groupId);
        consumer.setInstanceName(clientId);

        //consumer.setVipChannelEnabled(false);

        if (isBroadcasting) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }
    }


}
