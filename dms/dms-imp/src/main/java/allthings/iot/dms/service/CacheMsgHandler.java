package allthings.iot.dms.service;

import allthings.iot.common.AbstractDeviceMessagePipe;
import allthings.iot.common.Callback;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.usual.GroupConsts;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.dms.DmsConfig;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IConsumerConfig;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * @author :  sylar
 * @FileName :  CacheMsgHandler
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
@Component
public class CacheMsgHandler extends AbstractDeviceMessagePipe {

    @Autowired
    DeviceManageServiceImpl deviceManageService;

    @Autowired
    private DmsConfig dmsConfig;

    private IConsumer consumer;

    @PostConstruct
    public void initialize() {
        consumer = dmsConfig.getFactory().createConsumer(new IConsumerConfig() {
            @Override
            public String getConsumerId() {
                return GroupConsts.IOT_DAS_TO_DMS_GROUP;
            }
        });

        init();
    }

    @Override
    public void input(Callback<IMsg> callback) {
        String topic = TopicConsts.DAS_TO_DMS;
        consumer.subscribe(topic, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> messages) {
                for (RocketMsg rocketMsg : messages) {
                    callback.onSuccess(convert(rocketMsg.getContent()));
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
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
    public void output(IMsg msg) throws Exception {
        deviceManageService.processMsg(msg);
    }
}
