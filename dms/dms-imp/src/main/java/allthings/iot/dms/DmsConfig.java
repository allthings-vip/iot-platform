package allthings.iot.dms;

import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author :  sylar
 * @FileName :  DmsConfig
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Configuration
public class DmsConfig {

    @Value("${zookeeper.connectString}")
    String zkConnectString;

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    private IFactory factory;

    @PostConstruct
    public void init() throws Exception {
        factory = RocketMQUtil.createOwnFactory(brokers);
    }

    public String getZkConnectString() {
        return zkConnectString;
    }

    public IFactory getFactory() {
        return factory;
    }
}
