package allthings.iot.dos.consumer;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.common.msg.DeviceEventMsg;
import allthings.iot.common.msg.DeviceInfoMsg;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.common.usual.TopicConsts;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.dos.IotDosServiceConfig;
import allthings.iot.dos.api.IotConsumerService;
import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.api.IotLoggerService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.util.redis.ICentralCacheService;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotDeviceConnectConsumer
 * @CreateDate :  2018-5-25
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
public class IotDeviceConnectConsumer {
    private static final String IOT_DOS_GROUP = "iot-dos-group";
    private static final String IOT_DOS_DIRECTIVE_GROUP = "iot-dos-directive-group";
    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceConnectConsumer.class);
    @Autowired
    private IotDosServiceConfig iotDosServiceConfig;
    private IConsumer consumer;
    /**
     * 指令消费
     */
    private IConsumer directiveConsumer;
    /**
     * 日志
     */
    private IConsumer loggerConsumer;
    /**
     * VIS
     */
    private IConsumer visConsumer;
    @Autowired
    private IotDeviceService iotDeviceService;
    @Autowired
    private ICentralCacheService cache;
    @Autowired
    private IotConsumerService consumerService;
    @Autowired
    private IotLoggerService iotLoggerService;

    public void handleMessage(String topic, String content) {

        //LOGGER.info("消费日志：topic:{}, content:{}", topic, content);

        CacheMsgWrap wrap = JSON.parseObject(content, CacheMsgWrap.class);
        if (wrap == null) {
            return;
        }

        IMsg msg = wrap.getMsg();
        try {
            if (msg instanceof DeviceConnectionMsg) {
                DeviceConnectionMsg dataMsg = (DeviceConnectionMsg) msg;
                iotDeviceService.saveDeviceStatus(dataMsg);
                // 保存设备状态改变日志
                consumerService.saveDeviceLogger(dataMsg);
            }

            if (msg instanceof DeviceDataMsg) {
                Map<String, Object> params = msg.getParams();
                //去重
                String key = String.format("%s_%s_%s_%s", msg.getSourceDeviceType(), msg.getSourceDeviceId(),
                        msg.getMsgCode(), params.get(GpsMsgParam.ATTR_GPS_DATETIME));
                String value = cache.getObject(key, String.class);
                if (StringUtils.isNotBlank(value)) {
                    return;
                }

                int size = params.size() - 1;
                if (params.get(GpsMsgParam.ATTR_GPS_LONGITUDE) != null) {
                    size += 8;
                }
                Long time = DateUtils.truncate(new Date(
                        System.currentTimeMillis() + 24 * 60 * 60 * 1000), Calendar.DATE).getTime();
                Long activeTime = (time - System.currentTimeMillis()) / 1000;
                cache.putObjectWithExpireTime(key, key, activeTime.intValue());

                //总数
                cache.updateMapIncrement(Constants.DOS_POINT_COUNT_KEY, Constants.POINT_TOTAL_COUNT, size);
                //今日数量增加
                cache.updateMapIncrement(Constants.DOS_POINT_COUNT_KEY, Constants.POINT_TODAY_COUNT, size);

                ResultDTO<List<Long>> bizReturn = iotDeviceService.getIotProjectIdByDeviceCode(msg.getSourceDeviceId());
                if (!bizReturn.isSuccess()) {
                    bizReturn = iotDeviceService.getIotProjectIdByDeviceCode(
                            msg.getSourceDeviceType() + msg.getSourceDeviceId());
                    if (!bizReturn.isSuccess()) {
                        return;
                    }
                }
                List<Long> iotProjectIds = bizReturn.getData();
                for (Long iotProjectId : iotProjectIds) {
                    //项目内总数
                    cache.updateMapIncrement(Constants.DOS_PROJECT_POINT_TOTAL_COUNT_KEY, String.valueOf(iotProjectId), size);
                    //项目内今日数量增加
                    cache.updateMapIncrement(Constants.DOS_PROJECT_POINT_TODAY_COUNT_KEY, String.valueOf(iotProjectId), size);
                }

            }

            // 保存设备通道
            if (msg instanceof DeviceInfoMsg) {
                consumerService.saveDevicePass(msg);
            }
        } catch (Exception e) {
            LOGGER.error("handleMessage error " + e.getMessage());
        }

        // 保存平台下发指令日志
        if (msg instanceof DeviceMsg) {
            consumerService.saveDeviceLogger(msg);
        }

        // 保存事件上报日志
        if (msg instanceof DeviceEventMsg) {
            consumerService.saveDeviceLogger(msg);
        }
    }

    @PostConstruct
    public void init() {
        consumer = iotDosServiceConfig.getFactory().createConsumer(() -> IOT_DOS_GROUP);

        consumer.subscribe(TopicConsts.DMS_TO_APS, null, new
                IRocketMsgListener() {
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
                });
        getDirectiveConsumer();
        getLoggerConsumer();
        getVisResult();
    }

    private void getDirectiveConsumer() {
        directiveConsumer = iotDosServiceConfig.getFactory().createConsumer(() -> IOT_DOS_DIRECTIVE_GROUP);
        directiveConsumer.subscribe(TopicConsts.DMS_TO_DAS, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> list) {
                for (RocketMsg rocketMsg : list) {
                    handleMessage(rocketMsg.getTopic(), rocketMsg.getContent());
                }
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private void getLoggerConsumer() {
        loggerConsumer = iotDosServiceConfig.getFactory().createConsumer(() -> Constants.IOT_DOS_LOGGER_GROUP);
        loggerConsumer.subscribe(Constants.IOT_DOS_LOGGER_TOPIC, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> list) throws Exception {
                for (RocketMsg rocketMsg : list) {
                    String content = rocketMsg.getContent();
                    if (StringUtils.isBlank(content)) {
                        continue;
                    }
                    IotLogDTO logDTO = JSON.parseObject(content, IotLogDTO.class);
                    iotLoggerService.saveIotDeviceLogger(logDTO);
                }
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    /**
     * 处理VIS返回结果
     */
    private void getVisResult() {
        visConsumer = iotDosServiceConfig.getFactory().createConsumer(() -> Constants.IOT_AEP_TO_DOS_GROUP);
        visConsumer.subscribe(Constants.IOT_AEP_TO_DOS_TOPIC, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> list) throws Exception {
                for (RocketMsg rocketMsg : list) {
                    String content = rocketMsg.getContent();
                    LOGGER.info("接收VIS数据：" + content);
                    if (StringUtils.isBlank(content)) {
                        continue;
                    }
                    IotVisResultDTO iotVisResultDTO = JSON.parseObject(content, IotVisResultDTO.class);
                    if (iotVisResultDTO == null) {
                        continue;
                    }
                    String msgId = iotVisResultDTO.getSequence();
                    cache.putObjectWithExpireTime(msgId, content, Constants.VIS_RESULT_LIVE);
                }
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }
}
