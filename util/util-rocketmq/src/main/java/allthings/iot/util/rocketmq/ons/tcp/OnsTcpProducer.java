package allthings.iot.util.rocketmq.ons.tcp;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import allthings.iot.util.rocketmq.AbsProcuder;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import allthings.iot.util.rocketmq.ons.AbsOnsFactory;

import java.util.Properties;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpProducer extends AbsProcuder implements IProducer {

    AbsOnsFactory factory;
    Producer producer;

    protected OnsTcpProducer(AbsOnsFactory factory, IProducerConfig config) {
        super(config);
        this.factory = factory;
        init();
    }

    void init() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, factory.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, factory.getSecretKey());
        properties.put(PropertyKeyConst.ONSAddr, factory.getServerEndpoint());
        properties.put(PropertyKeyConst.ProducerId, config.getProducerId());
        producer = ONSFactory.createProducer(properties);
        producer.start();
    }

    @Override
    public Object syncSend(RocketMsg msg) throws Exception {
        return this.producer.send(msg.getOnsMessage());
    }

    @Override
    public void shutdown() {

    }
}
