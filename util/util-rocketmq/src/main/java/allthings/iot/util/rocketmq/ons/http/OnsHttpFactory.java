package allthings.iot.util.rocketmq.ons.http;

import allthings.iot.util.rocketmq.*;
import allthings.iot.util.rocketmq.ons.AbsOnsFactory;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpFactory extends AbsOnsFactory implements IFactory {
    public OnsHttpFactory(String accessKey, String secretKey, String serverEndpoint) {
        super(accessKey, secretKey, serverEndpoint);
    }

    @Override
    public IProducer createProducer(IProducerConfig config) {
        return new OnsHttpProducer(this, config);
    }

    @Override
    public IConsumer createConsumer(IConsumerConfig config) {
        return new OnsHttpConsumer(this, config);
    }
}
