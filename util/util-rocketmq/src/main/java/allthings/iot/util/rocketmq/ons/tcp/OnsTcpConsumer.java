package allthings.iot.util.rocketmq.ons.tcp;

import com.aliyun.openservices.ons.api.*;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import allthings.iot.util.rocketmq.AbsConsumer;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IConsumerConfig;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import allthings.iot.util.rocketmq.ons.AbsOnsFactory;

import java.util.Properties;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpConsumer extends AbsConsumer implements IConsumer {

    AbsOnsFactory factory;
    Consumer consumer;

    protected OnsTcpConsumer(AbsOnsFactory factory, IConsumerConfig config) {
        super(config);
        this.factory = factory;
        init();
    }

    void init() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, factory.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, factory.getSecretKey());
        properties.put(PropertyKeyConst.ONSAddr, factory.getServerEndpoint());
        properties.put(PropertyKeyConst.ConsumerId, config.getConsumerId());
        consumer = ONSFactory.createConsumer(properties);
    }

    @Override
    public void subscribe(String topic, String[] tags, IRocketMsgListener listener) {
        unsubscribe();

        String subExpression = "*";
        if (tags != null && tags.length > 0) {
            subExpression = Joiner.on("||").skipNulls().join(tags);
        }


        consumer.subscribe(topic, subExpression, new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                String content = new String(message.getBody(), Charsets.UTF_8);
                System.out.println("Receive Msg: " + message);
                try {
                    RocketMsg msg = new RocketMsg(topic, content);
                    msg.setTags(message.getTag());
                    msg.setKeys(message.getKey());
                    msg.setExt(message);
                    listener.onSuccess(Lists.newArrayList(msg));
                    return Action.CommitMessage;
                } catch (Exception e) {
                    String err = String.format("处理消息发生异常. msgId:%s\ncontent:%s\n%s", message.getMsgID(), content, e
                            .getMessage());
                    System.out.println(err);
                    e.printStackTrace();

                    return Action.ReconsumeLater;
                }
            }
        });

        consumer.start();
    }

    @Override
    public void unsubscribe() {
        consumer.shutdown();
    }
}
