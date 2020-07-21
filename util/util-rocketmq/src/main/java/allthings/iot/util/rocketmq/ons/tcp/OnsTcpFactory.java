package allthings.iot.util.rocketmq.ons.tcp;

import allthings.iot.util.rocketmq.*;
import allthings.iot.util.rocketmq.ons.AbsOnsFactory;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpFactory extends AbsOnsFactory implements IFactory {
    public OnsTcpFactory(String accessKey, String secretKey, String serverEndpoint) {
        super(accessKey, secretKey, serverEndpoint);
    }

    @Override
    public IProducer createProducer(IProducerConfig config) {
        return new OnsTcpProducer(this, config);
    }

    @Override
    public IConsumer createConsumer(IConsumerConfig config) {
        return new OnsTcpConsumer(this, config);
    }
}
