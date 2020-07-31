package allthings.iot.dos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;

/**
 * @author :  luhao
 * @FileName :  IotDosBizConfig
 * @CreateDate :  2018/5/4
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Configuration
public class IotDosBizConfig {
    @Value("${zookeeper.connectString}")
    String zkConnectString;

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    @Bean
    public IFactory getFactory() {
        return RocketMQUtil.createOwnFactory(brokers);
    }

}
