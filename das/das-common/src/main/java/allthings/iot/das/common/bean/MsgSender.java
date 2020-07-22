package allthings.iot.das.common.bean;

import com.google.common.base.Strings;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import allthings.iot.common.AbstractDeviceMessagePipe;
import allthings.iot.common.Callback;
import allthings.iot.common.msg.DeviceOtaMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.usual.GroupConsts;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.common.event.OtaNewTaskEvent;
import allthings.iot.util.misc.NetUtils;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IConsumerConfig;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * @author :  sylar
 * @FileName :  MsgSender
 * @CreateDate :  2017/11/08
 * @Description :  消息发送器:  将消息发给 channel
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
public class MsgSender extends AbstractDeviceMessagePipe {
    private static final Logger LOG = LoggerFactory.getLogger(MsgSender.class);

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private DasConfig dasConfig;

    @Autowired
    private ChannelCache channelCache;

    private IConsumer consumer;

    @PostConstruct
    public void initialize() {
        consumer = dasConfig.getFactory().createConsumer(new IConsumerConfig() {
            @Override
            public String getConsumerId() {
                return String.join("-", GroupConsts.IOT_DMS_TO_DAS_GROUP, NetUtils.getHostMac());
            }
        });

        super.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (consumer != null) {
            consumer.unsubscribe();
            consumer = null;
        }
    }

    @Override
    public void input(final Callback<IMsg> callback) {
        String topic = TopicConsts.DMS_TO_DAS;

        //根据nodeid去订阅
        try {
            consumer.subscribe(topic, new String[]{dasConfig.getDasNodeId()}, new IRocketMsgListener() {
                @Override
                public void onSuccess(List<RocketMsg> messages) {
                    for (RocketMsg rocketMsg : messages) {
                        IMsg msg = convert(rocketMsg.getContent());
                        switch (msg.getMsgType()) {
                            case DeviceOta:
                                ctx.publishEvent(new OtaNewTaskEvent(this, (DeviceOtaMsg) msg));
                                break;
                            default:
                                callback.onSuccess(msg);
                                break;
                        }

                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    callback.onFailure(throwable);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void output(IMsg msg) {
        sendMsg(msg);
    }

    public void sendMsg(IMsg msg) {
        String targetDeviceType = msg.getTargetDeviceType();
        String targetDeviceId = msg.getTargetDeviceId();
        String platformCode = msg.getPlatformCode();

        if (Strings.isNullOrEmpty(targetDeviceType)) {
            LOG.warn("sendMsg error: targetDeviceType is null or empty");
            return;
        }

        if (Strings.isNullOrEmpty(targetDeviceId)) {
            LOG.warn("sendMsg error: targetDeviceId is null or empty");
            return;
        }

        //channel查找规则: deviceId 或者 deviceType + deviceId
        Channel channel = channelCache.get(platformCode == null ? targetDeviceId : platformCode);
        if (channel == null) {
            channel = channelCache.get(targetDeviceType + targetDeviceId);
        }

        if (channel == null) {
            LOG.warn("sendMsg error. can't find channel for deviceId: {}", targetDeviceId);
            return;
        }

        NettyUtil.writeData(channel, msg);
        LOG.info("send message : {} -> {} \n message: {}", msg.getSourceDeviceId(), targetDeviceId, msg);
    }

}
