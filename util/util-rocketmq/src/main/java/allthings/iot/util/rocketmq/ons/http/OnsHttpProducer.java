package allthings.iot.util.rocketmq.ons.http;

import allthings.iot.util.rocketmq.AbsProcuder;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import allthings.iot.util.rocketmq.ons.AbsOnsFactory;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpProducer extends AbsProcuder implements IProducer {

    AbsOnsFactory factory;

    protected OnsHttpProducer(AbsOnsFactory factory, IProducerConfig config) {
        super(config);
        this.factory = factory;
        init();
    }

    void init() {
    }

    @Override
    public Object syncSend(RocketMsg msg) throws Exception {
        HttpResult result = HttpUtil.sendMsg(
                factory.getServerEndpoint(),
                factory.getAccessKey(),
                factory.getSecretKey(),
                config.getProducerId(),
                msg.getTopic(),
                msg.getTags(),
                msg.getKeys(),
                msg.getContent());

        if (result.getStatusCode() == HttpStatusCode.OK_WRITE) {
            return result;
        } else {
            throw new Exception(result.toString());
        }
    }

    @Override
    public void shutdown() {

    }
}
