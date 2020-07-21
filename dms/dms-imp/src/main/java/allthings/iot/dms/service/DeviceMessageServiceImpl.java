package allthings.iot.dms.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import allthings.iot.common.msg.DasConnectionMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.common.usual.GroupConsts;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.dms.DmsConfig;
import allthings.iot.dms.IDeviceMessageService;
import allthings.iot.dms.bean.MsgSender;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceMessageServiceImpl
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
@Service
public class DeviceMessageServiceImpl implements IDmsMsgProcessor<IMsg>, IDeviceMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceMessageServiceImpl.class);

    @Autowired
    private DmsConfig dmsConfig;

    @Autowired
    private MsgSender msgSender;

    private IProducer producer;

    @PostConstruct
    public void init() {
        producer = dmsConfig.getFactory().createProducer(new IProducerConfig() {
            @Override
            public String getProducerId() {
                return String.join("-", GroupConsts.IOT_DMS_TO_APS_GROUP, this.getClass().getCanonicalName());
            }
        });
    }

    @Override
    public void processMsg(IMsg msg) throws Exception {
        if (msg instanceof DasConnectionMsg) {
            //das node connection msg
            return;
        }
        if (Strings.isNullOrEmpty(msg.getSourceDeviceId())
                || Strings.isNullOrEmpty(msg.getMsgCode())) {
            LOG.warn("sourceDeviceId is null or msgCode is null:{}", msg);
            return;
        }


        String topic = TopicConsts.DMS_TO_APS;
        CacheMsgWrap wrap = new CacheMsgWrap(msg);
        LOG.info("DMS publish DeviceMessage\n{}", msg);

        RocketMsg rocketMsg = new RocketMsg(topic);
        rocketMsg.setContent(JSON.toJSONString(wrap));

        //append tag
        List<String> tagsList = Lists.newArrayList();
        //tagsList.add(msg.getMsgCode());
        tagsList.add(msg.getSourceDeviceType());
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

    @Override
    public void sendMsg(IMsg msg) throws Exception {
        LOG.info("send msg to device {}", msg.getSourceDeviceId());
        msgSender.sendToQueue(msg);
    }
}
