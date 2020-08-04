package allthings.iot.ktv;

import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :  luhao
 * @FileName :  VehicleDataConfig
 * @CreateDate :  2018/01/15
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
public class KtvDataConfig {

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    private IFactory factory;

    @Bean
    public IFactory getFactory() {
        return  RocketMQUtil.createOwnFactory(brokers);
    }
}
