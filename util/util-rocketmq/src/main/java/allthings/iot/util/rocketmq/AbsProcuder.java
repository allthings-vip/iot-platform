package allthings.iot.util.rocketmq;

/**
 * Created by sylar on 2017/1/6.
 */
public class AbsProcuder {

    protected IProducerConfig config;

    public AbsProcuder(IProducerConfig config) {
        this.config = config;
    }

    public IProducerConfig getConfig() {
        return config;
    }
}
