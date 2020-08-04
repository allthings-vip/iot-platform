package allthings.iot.ktv.consumer;

import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.ktv.KtvDataConfig;
import allthings.iot.ktv.util.Constant;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author tyf
 * @date 2019/01/23 14:01
 */
@Component
public class KtvDataProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KtvDataProducer.class);

    private IProducer producer;

    @Autowired
    private KtvDataConfig ktvDataConfig;

    @PostConstruct
    public void init() {
        producer = ktvDataConfig.getFactory().createProducer(() ->
                String.join("-", Constant.IOT_LBS_P_RETRY_GROUP, this.getClass().getSimpleName()));
    }

    @PreDestroy
    private void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public void sendToQueue(IMsg msg) {
        try {
            RocketMsg rocketMsg = new RocketMsg(Constant.TOPIC_LBS_P_RETRY);

            CacheMsgWrap wrap = new CacheMsgWrap(msg);
            rocketMsg.setContent(JSON.toJSONString(wrap));

            producer.syncSend(rocketMsg);
        } catch (Exception e) {
            LOGGER.error("sendToQueue error:{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
