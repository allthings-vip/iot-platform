package allthings.iot.dos.consumer;

import allthings.iot.dos.IotDosBizConfig;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.query.IotLogDTO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.msg.RocketMsg;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author tyf
 * @date 2019/03/14 17:10
 */
@Component
public class IotLoggerProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotLoggerProducer.class);

    private IProducer producer;

    @Autowired
    private IotDosBizConfig iotDosBizConfig;

    @PostConstruct
    public void init() {
        producer = iotDosBizConfig.getFactory().createProducer(() ->
                String.join("-", Constants.IOT_DOS_LOGGER_GROUP, this.getClass().getSimpleName()));
    }

    @PreDestroy
    private void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public void sendToQueue(IotLogDTO iotLogDTO) {
        try {
            RocketMsg rocketMsg = new RocketMsg(Constants.IOT_DOS_LOGGER_TOPIC);
            rocketMsg.setContent(JSON.toJSONString(iotLogDTO));
            producer.syncSend(rocketMsg);
        } catch (Exception e) {
            LOGGER.error("sendToQueue error:{}", e.getMessage());
            e.printStackTrace();
        }
    }

}
