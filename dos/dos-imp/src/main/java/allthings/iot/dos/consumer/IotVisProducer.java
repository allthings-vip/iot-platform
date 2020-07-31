package allthings.iot.dos.consumer;

import allthings.iot.dos.IotDosBizConfig;
import allthings.iot.dos.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.msg.RocketMsg;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-04-17 09:39
 */
@Component
public class IotVisProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotVisProducer.class);

    @Autowired
    private IotDosBizConfig iotDosBizConfig;

    private IProducer producer;

    @PostConstruct
    public void init() {
        producer = iotDosBizConfig.getFactory().createProducer(() ->
                String.join("-", Constants.IOT_DOS_TO_AEP_GROUP, this.getClass().getSimpleName()));
    }

    @PreDestroy
    private void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public void sendToQueue(String toVisContent) {
        try {
            RocketMsg rocketMsg = new RocketMsg(Constants.IOT_DOS_TO_AEP_TOPIC);
            rocketMsg.setContent(toVisContent);
            LOGGER.info("VIS发送消息：" + toVisContent);
            producer.syncSend(rocketMsg);
        } catch (Exception e) {
            LOGGER.error("sendToQueue error:{}", e.getMessage());
        }
    }
}
