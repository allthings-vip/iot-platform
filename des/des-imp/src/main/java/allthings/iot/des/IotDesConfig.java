package allthings.iot.des;

import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tyf
 * @date 2019/03/05 10:36
 */
@Configuration
public class IotDesConfig {

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    @Bean
    public IFactory getFactory() {
        return RocketMQUtil.createOwnFactory(brokers);
    }

}
