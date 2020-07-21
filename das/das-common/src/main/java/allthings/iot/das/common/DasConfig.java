package allthings.iot.das.common;

import com.google.common.base.Preconditions;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author :  sylar
 * @FileName :  DasConfig
 * @CreateDate :  2017/11/08
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
@EnableConfigurationProperties(value = {DasProperties.class})
public class DasConfig {
    @Autowired
    private DasProperties dasProperties;

    private IFactory factory;

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    @PostConstruct
    private void init() {
        Preconditions.checkNotNull(dasProperties, "dasProperties can not be null");
        factory = RocketMQUtil.createOwnFactory(brokers);
    }

    @PreDestroy
    private void dispose() {
    }

    public String getDasNodeId() {
        return dasProperties.getNodeId();
    }

    public DasProperties getDasProperties() {
        return dasProperties;
    }

    public IFactory getFactory() {
        return factory;
    }
}
