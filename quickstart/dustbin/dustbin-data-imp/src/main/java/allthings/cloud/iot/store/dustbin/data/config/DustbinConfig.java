package tf56.cloud.iot.store.dustbin.data.config;

import allthings.iot.dms.Dms;
import allthings.iot.dms.IDeviceManageService;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author :  sylar
 * @FileName :  DustbinConfig
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
public class DustbinConfig {

    @Value("${zookeeper.connectString}")
    String zkConnectString;

    IDeviceManageService dms;

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    private IFactory factory;

    @PostConstruct
    public void init() {
        dms = Dms.getService(zkConnectString);
        factory = RocketMQUtil.createOwnFactory(brokers);
    }

    public String getZkConnectString() {
        return zkConnectString;
    }

    public IDeviceManageService getDms() {
        return dms;
    }

    public IFactory getFactory() {
        return factory;
    }
}
