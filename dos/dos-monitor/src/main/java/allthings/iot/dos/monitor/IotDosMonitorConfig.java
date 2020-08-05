package allthings.iot.dos.monitor;

import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author :  txw
 * @FileName :  allthings.iot.dos.monitor.IotDosMonitorConfig
 * @CreateDate :  2019/5/7
 * @Description :  dmp
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
public class IotDosMonitorConfig {

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    //    @ConditionalOnBean(value = {IFactory.class})
//    @Bean
    public IFactory getIFactory() {
        return RocketMQUtil.createOwnFactory(brokers);
    }

}
