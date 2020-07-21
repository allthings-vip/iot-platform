package allthings.iot.util.rocketmq.ons.mqtt;

import allthings.iot.util.rocketmq.*;
import allthings.iot.util.rocketmq.ons.AbsOnsFactory;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttFactory extends AbsOnsFactory implements IFactory {
    private String clientId;

    public OnsMqttFactory(String accessKey, String secretKey, String serverEndpoint, String clientId) {
        super(accessKey, secretKey, serverEndpoint);
        this.clientId = clientId;
    }

    @Override
    public IProducer createProducer(IProducerConfig config) {
        return new OnsMqttProducer(this, config);
    }

    @Override
    public IConsumer createConsumer(IConsumerConfig config) {
        return new OnsMqttConsumer(this, config);
    }

    public String getClientId() {
        return clientId;
    }
}
