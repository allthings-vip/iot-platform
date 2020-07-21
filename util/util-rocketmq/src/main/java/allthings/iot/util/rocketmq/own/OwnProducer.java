package allthings.iot.util.rocketmq.own;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import allthings.iot.util.rocketmq.AbsProcuder;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;

/**
 * Created by sylar on 2017/1/6.
 */
public class OwnProducer extends AbsProcuder implements IProducer {

    OwnFactory factory;
    DefaultMQProducer producer;

    protected OwnProducer(OwnFactory factory, IProducerConfig config) {
        super(config);
        this.factory = factory;
        init();
    }

    void init() {
        producer = new DefaultMQProducer();
        producer.setNamesrvAddr(factory.getNameServerAddr());
        producer.setProducerGroup(config.getProducerId());
        producer.setVipChannelEnabled(false);

        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object syncSend(RocketMsg msg) throws Exception {
        return this.producer.send(msg.getOwnMessage());
    }

    @Override
    public void shutdown() {
        if (producer != null) {
            producer.shutdown();
        }
    }
}
