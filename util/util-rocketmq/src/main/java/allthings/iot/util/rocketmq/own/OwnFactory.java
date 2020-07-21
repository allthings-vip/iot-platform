package allthings.iot.util.rocketmq.own;

import allthings.iot.util.rocketmq.*;

/**
 * Created by sylar on 2017/1/6.
 */
public class OwnFactory implements IFactory {

    String nameServerAddr;

    public OwnFactory(String nameServerAddr) {
        this.nameServerAddr = nameServerAddr;
    }

    public String getNameServerAddr() {
        return nameServerAddr;
    }

    @Override
    public IProducer createProducer(IProducerConfig config) {
        return new OwnProducer(this, config);
    }

    @Override
    public IConsumer createConsumer(IConsumerConfig config) {
        return new OwnConsumer(this, config);
    }
}
