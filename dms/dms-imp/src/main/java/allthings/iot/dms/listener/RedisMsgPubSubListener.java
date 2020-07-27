package allthings.iot.dms.listener;

import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.dms.DmsCacheKeys;
import allthings.iot.dms.dto.DeviceStatusDto;
import allthings.iot.dms.service.DasStatusServiceImpl;
import allthings.iot.dms.service.DeviceMessageServiceImpl;
import allthings.iot.util.redis.AbstractMessageListener;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author :  luhao
 * @FileName :  RedisMsgPubSubListener
 * @CreateDate :  2018-10-16
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
@Component
public class RedisMsgPubSubListener extends AbstractMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMsgPubSubListener.class);

    @Autowired
    private DasStatusServiceImpl dasStatusServiceImpl;

    private CacheCloudRedisFactory cache;

    @Autowired
    private DeviceMessageServiceImpl deviceMessageServiceImpl;

    @PostConstruct
    private void init() throws Exception {
        cache = new CacheCloudRedisFactory();
    }

    public RedisMsgPubSubListener() {

    }

    @Override
    protected void handleMessage(String topic, String jsonMsg) throws Exception {

    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        if (StringUtils.isBlank(message)) {
            return;
        }

        int fixedLen = 5;
        String[] deviceIdArray = message.split(":");
        if (deviceIdArray.length != fixedLen) {
            return;
        }

        long lastTime = System.currentTimeMillis();
        String deviceId = deviceIdArray[3] + deviceIdArray[4];
        String ccsKey = DmsCacheKeys.getCcsKeyForDeviceStatus(deviceId);
        DeviceStatusDto pojo = JSON.parseObject(cache.get(ccsKey), DeviceStatusDto.class);
        pojo.setOfflineTime(lastTime);
        pojo.setConnected(false);

        //向上抛连接消息
        DeviceConnectionMsg msg = new DeviceConnectionMsg();
        msg.setDasNodeId(pojo.getNodeId());
        msg.setSourceDeviceId(deviceIdArray[4]);
        msg.setSourceDeviceType(deviceIdArray[3]);
        msg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());
        msg.setTerminalIp(pojo.getTerminalIp());
        msg.setConnected(false);
        msg.setOccurTime(lastTime);

        try {
            deviceMessageServiceImpl.processMsg(msg);
            LOGGER.info("订阅连接消息：{}", JSON.toJSONString(msg));
            cache.set(ccsKey, JSON.toJSONString(pojo), -1);
            dasStatusServiceImpl.updateDeviceConnection(pojo.getNodeId(), deviceId, false);
        } catch (Exception e) {
            LOGGER.error("deal status error", e);
        }
    }
}