package allthings.iot.des.consumer;

import com.alibaba.fastjson.JSON;
import allthings.iot.des.dto.constant.Constant;
import allthings.iot.des.dto.query.IotDesDeviceEventSaveDto;
import allthings.iot.des.api.IotDesDeviceEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import allthings.iot.common.msg.DeviceEventMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/03/04 9:18
 */
@Component
public class IotDesConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDesConsumer.class);

    @Autowired
    private IFactory factory;

    @Autowired
    private IotDesDeviceEventService iotDesDeviceEventService;

    private IConsumer consumer;

    private IConsumer publishConsumer;

    @PostConstruct
    public void init() {
        getEventConsumer();
        getPublishConsumer();
    }

    public void getEventConsumer() {
        consumer = factory.createConsumer(() -> Constant.IOT_DES_GROUP);

        consumer.subscribe(TopicConsts.DMS_TO_APS, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> message) throws Exception {
                for (RocketMsg rocketMsg : message) {
                    handleMessage(rocketMsg.getTopic(), rocketMsg.getContent());
                }
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private void getPublishConsumer() {
        publishConsumer = factory.createConsumer(() -> Constant.IOT_DES_PUBLISH_GROUP);

        publishConsumer.subscribe(TopicConsts.DMS_TO_APS, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> list) throws Exception {
                for (RocketMsg rocketMsg : list) {
                    pushMessage(rocketMsg.getTopic(), rocketMsg.getContent());
                }
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });

    }


    private void handleMessage(String topic, String content) {
        CacheMsgWrap wrap = JSON.parseObject(content, CacheMsgWrap.class);
        if (wrap == null) {
            return;
        }
        IMsg msg = wrap.getMsg();
        if (msg instanceof DeviceEventMsg) {
            try {
                DeviceEventMsg eventMsg = (DeviceEventMsg) msg;
                Map<String, Object> params = msg.getParams();
                IotDesDeviceEventSaveDto desDeviceEventSaveDto = new IotDesDeviceEventSaveDto();
                desDeviceEventSaveDto.setDeviceCode(eventMsg.getSourceDeviceId());
                desDeviceEventSaveDto.setEventData(String.valueOf(params.get("eventData")));
                desDeviceEventSaveDto.setEventDescription(eventMsg.getEventDescription());
                desDeviceEventSaveDto.setEventSource(String.valueOf(params.get("eventSource")));
                desDeviceEventSaveDto.setEventTime(Long.valueOf(String.valueOf(params.get("eventTime"))));
                desDeviceEventSaveDto.setEventTypeCode(String.valueOf(params.get("eventTypeCode")));
                desDeviceEventSaveDto.setImage(params.get("image") == null ? null : String.valueOf(params.get("image")));
                desDeviceEventSaveDto.setVideo(params.get("video") == null ? null : String.valueOf(params.get("video")));
                desDeviceEventSaveDto.setCreateOperatorId(1L);
                iotDesDeviceEventService.saveIotDesDeviceEvent(desDeviceEventSaveDto);
                LOGGER.info("事件保存成功！事件内容：{}", JSON.toJSONString(eventMsg));
            } catch (Exception e) {
                LOGGER.error("保存设备事件异常，{}", e.toString());
            }

        }
    }

    private void pushMessage(String topic, String content) throws Exception {
        CacheMsgWrap wrap = JSON.parseObject(content, CacheMsgWrap.class);
        if (wrap == null) {
            return;
        }
        IMsg msg = wrap.getMsg();
        if (msg instanceof DeviceEventMsg) {
            LOGGER.info("开始推送事件");
            iotDesDeviceEventService.pushDeviceEvent((DeviceEventMsg) msg);
            LOGGER.info("事件推送成功， 事件内容：{}", JSON.toJSONString(msg));
        }
    }

}
