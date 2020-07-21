package allthings.iot.das.common.bean;

import com.alibaba.fastjson.JSON;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.common.usual.GroupConsts;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.das.common.DasConfig;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * @author :  sylar
 * @FileName :  MsgThrower
 * @CreateDate :  2017/11/08
 * @Description :  消息抛掷器: 将消息抛给队列
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
public class MsgThrower {

    private static final Logger LOG = LoggerFactory.getLogger(MsgThrower.class);

    @Autowired
    DasConfig dasConfig;

    private IProducer producer;

    @PostConstruct
    public void init() {
        producer = dasConfig.getFactory().createProducer(new IProducerConfig() {
            @Override
            public String getProducerId() {
                return String.join("-", GroupConsts.IOT_DAS_TO_DMS_GROUP, MsgThrower.class.getSimpleName());
            }
        });
    }

    public void sendToQueue(IMsg msg) {
        try {
            String topic = TopicConsts.DAS_TO_DMS;

            RocketMsg rocketMsg = new RocketMsg(topic);

            CacheMsgWrap wrap = new CacheMsgWrap(msg);
            rocketMsg.setContent(JSON.toJSONString(wrap));

            producer.syncSend(rocketMsg);
        } catch (Exception e) {
            LOG.error("sendToQueue error:{}", e.getMessage());
            e.printStackTrace();
        }

    }

    @PreDestroy
    private void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

}
