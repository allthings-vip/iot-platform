package allthings.iot.bsj.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import allthings.iot.dms.Dms;
import allthings.iot.dms.IDeviceManageService;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;

import javax.annotation.PostConstruct;

/**
 * @author :  luhao
 * @FileName :  BsjDataConfig
 * @CreateDate :  2018/01/12
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
public class BsjDataConfig {

    @Value("${zookeeper.connectString}")
    String zkConnectString;

    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    private IFactory factory;

    private IDeviceManageService dms;

    @PostConstruct
    public void init() {
        factory = RocketMQUtil.createOwnFactory(brokers);
        dms = Dms.getService(zkConnectString);
    }

    public String getZkConnectString() {
        return zkConnectString;
    }

    public IFactory getFactory() {
        return factory;
    }

    public IDeviceManageService getDms() {
        return dms;
    }
}
