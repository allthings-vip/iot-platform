package allthings.iot.util.rocketmq;

/**
 * Created by sylar on 2017/1/6.
 */
public class AbsConsumer {

    protected IConsumerConfig config;

    public AbsConsumer(IConsumerConfig config) {
        this.config = config;
    }

    public IConsumerConfig getConfig() {
        return config;
    }
}
