package allthings.iot.ktv.consumer;

import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.ktv.KtvDataConfig;
import allthings.iot.ktv.api.IKtvDataService;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author tyf
 * @date 2018/10/12 18:41
 */
@Component
public class KtvDataConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KtvDataConsumer.class);

    private IConsumer consumer;

    @Autowired
    private KtvDataConfig ktvDataConfig;

    @Autowired
    private IKtvDataService ktvDataService;
    @Value("${iot.ktv.consumer.group}")
    private String consumerGroup;

    @PostConstruct
    public void init() {
        initKtvConsumer();
    }

    private void initKtvConsumer() {
        LOGGER.info("创建消费组，group：{}", consumerGroup);
        consumer = ktvDataConfig.getFactory().createConsumer(() -> consumerGroup);
        consumer.subscribe(TopicConsts.DMS_TO_APS, null, getListener());
    }

    /**
     * 处理订阅数据
     *
     * @param topic
     * @param content 内容
     */
    private void handleMessage(String topic, String content) throws Exception {
        CacheMsgWrap wrap = JSON.parseObject(content, CacheMsgWrap.class);
        if (wrap == null) {
            return;
        }

        IMsg msg = wrap.getMsg();
        if (msg instanceof DeviceDataMsg) {
            DeviceDataMsg dataMsg = (DeviceDataMsg) msg;
            ktvDataService.handleKtvData(dataMsg);
        }
    }


    private IRocketMsgListener getListener() {
        return new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> messages) throws Exception {
                for (RocketMsg rocketMsg : messages) {
                    handleMessage(rocketMsg.getTopic(), rocketMsg.getContent());

                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
            }
        };
    }
}
