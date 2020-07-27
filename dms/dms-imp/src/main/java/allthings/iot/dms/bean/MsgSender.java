package allthings.iot.dms.bean;

import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.common.usual.GroupConsts;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.dms.DmsConfig;
import allthings.iot.dms.dto.DeviceStatusDto;
import allthings.iot.dms.service.DeviceStatusServiceImpl;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  MsgSender
 * @CreateDate :  2017/11/08
 * @Description : 消息发送器:  将消息发给 DAS
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class MsgSender {
    private static final Logger LOG = LoggerFactory.getLogger(MsgSender.class);

    @Autowired
    private DmsConfig dmsConfig;

    @Autowired
    private DeviceStatusServiceImpl deviceStatusServiceImpl;

    private IProducer producer;

    @PostConstruct
    public void init() {
        producer = dmsConfig.getFactory().createProducer(new IProducerConfig() {
            @Override
            public String getProducerId() {
                return String.join("-", GroupConsts.IOT_DMS_TO_APS_GROUP, this.getClass().getSimpleName());
            }
        });
    }

    public void sendToQueue(IMsg msg) throws Exception {
        if (msg == null) {
            return;
        }

        // 根据平台码获取状态
        String deviceStatusParam = msg.getPlatformCode() == null ? (msg.getTargetDeviceType() + msg.getTargetDeviceId()) : msg.getPlatformCode();

        DeviceStatusDto deviceStatus = deviceStatusServiceImpl.getDeviceStatus(deviceStatusParam);
        if (deviceStatus == null) {
            LOG.warn("can not send msg: deviceStatus not found, nodeId is unknown");
            return;
        }

        if (!deviceStatus.isConnected()) {
            LOG.warn("can not send msg: device is not connected.");
            return;
        }


        String nodeId = deviceStatus.getNodeId();
        CacheMsgWrap wrap = new CacheMsgWrap(msg);

        RocketMsg rocketMsg = new RocketMsg(TopicConsts.DMS_TO_DAS);
        rocketMsg.setContent(JSON.toJSONString(wrap));
        List<String> tagsList = Lists.newArrayList();
        tagsList.add(nodeId);
        //tagsList.add(msg.getMsgCode());
        //tagsList.add(msg.getSourceDeviceType());
        //tagsList.add(msg.getSourceDeviceId());
        //tagsList.add(String.valueOf(msg.getMsgType()));
        //tagsList.add(String.valueOf(msg.getOccurTime()));

        rocketMsg.setTagList(tagsList);
        producer.syncSend(rocketMsg);
    }

    @PreDestroy
    private void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }
}
