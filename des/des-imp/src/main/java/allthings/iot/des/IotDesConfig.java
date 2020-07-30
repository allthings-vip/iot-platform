package allthings.iot.des;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tf56.iot.util.rocketmq.IFactory;
import tf56.iot.util.rocketmq.RocketMQUtil;

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
