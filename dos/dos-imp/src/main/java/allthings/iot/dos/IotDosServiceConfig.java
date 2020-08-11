package allthings.iot.dos;

import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import allthings.iot.util.sms.SmsProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@EnableConfigurationProperties(value = {SmsProperties.class})
public class IotDosServiceConfig {
    @Value("${zookeeper.connectString}")
    String zkConnectString;

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    @Value("${iot.dos.sms.templateCode}")
    private String smsTemplateCode;

    @Value("${iot.dos.sms.signName}")
    private String smsSignName;

    @Bean
    public IFactory getFactory() {
        return RocketMQUtil.createOwnFactory(brokers);
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public String getSmsSignName() {
        return smsSignName;
    }
}
