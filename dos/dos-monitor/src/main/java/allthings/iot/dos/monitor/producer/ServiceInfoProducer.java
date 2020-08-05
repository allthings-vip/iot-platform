package allthings.iot.dos.monitor.producer;

import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.IotDosServiceInfoDto;
import allthings.iot.dos.monitor.IotDosMonitorConfig;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author :  txw
 * @FileName :  ServiceInfoProducter
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
@Component
public class ServiceInfoProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInfoProducer.class);
    private IProducer producer;

    @Autowired
    private IotDosMonitorConfig iotDosMonitorConfig;

    @PostConstruct
    public void init() {
        producer = iotDosMonitorConfig.getIFactory().createProducer(() -> String.join("-",
                Constants.IOT_DOS_MONITOR_GROUP, this.getClass().getSimpleName()));
    }

    @PreDestroy
    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public void sendToQueue(IotDosServiceInfoDto iotDosServiceInfoDto, String tag) {
        try {
            RocketMsg rocketMsg = new RocketMsg(Constants.IOT_DOS_MONITOR_TOPIC);
            rocketMsg.setTags(tag);
            rocketMsg.setContent(JSON.toJSONString(iotDosServiceInfoDto));
//            LOGGER.info("生产消息内容----------->>" + rocketMsg.getContent());
            producer.syncSend(rocketMsg);
        } catch (Exception e) {
            LOGGER.error("sendToQueue error:{}", e.getMessage());
            e.printStackTrace();
        }
    }
}