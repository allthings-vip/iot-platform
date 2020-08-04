package allthings.iot.dms.service;

import allthings.iot.common.Constant;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.usual.CacheKeys;
import allthings.iot.dms.DmsCacheKeys;
import allthings.iot.dms.IDeviceStatusService;
import allthings.iot.dms.dto.DeviceStatusDto;
import allthings.iot.dms.listener.RedisMsgPubSubListener;
import allthings.iot.util.redis.ICentralCacheService;
import allthings.iot.util.redis.ISubscribePublishService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author :  sylar
 * @FileName :  DeviceStatusServiceImpl
 * @CreateDate :  2017/11/08
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
@Service
public class DeviceStatusServiceImpl implements IDmsMsgProcessor<DeviceConnectionMsg>, IDeviceStatusService {
    @Autowired
    DasStatusServiceImpl dasStatusServiceImpl;

    private static final String EXPIRED_KEY = "__keyevent@*__:expired";

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStatusServiceImpl.class);

    @Autowired
    private ICentralCacheService cache;
    @Autowired
    private ISubscribePublishService sps;

    @Autowired
    private DeviceMessageServiceImpl deviceMessageServiceImpl;

    @Autowired
    private RedisMsgPubSubListener listener;

    private Executor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 2,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder()
            .setNameFormat("dms-status-expired-msg-log-%d").build());

    @PostConstruct
    private void init() {
        sps.subscribeMessage(listener, Lists.newArrayList(EXPIRED_KEY));
    }

    @Override
    public void processMsg(DeviceConnectionMsg msg) {
        try {
            String deviceId = msg.getPlatformCode() == null ? (msg.getSourceDeviceType() + msg.getSourceDeviceId()) : msg.getPlatformCode();
            String ccsKey = DmsCacheKeys.getCcsKeyForDeviceStatus(deviceId);
            //缓存中的设备状态对象，用于比对当前设备连接状态
            DeviceStatusDto pojo = cache.getObject(ccsKey, DeviceStatusDto.class);

            //重新初始化的设备状态对象
            DeviceStatusDto getPojo = new DeviceStatusDto();
            if (pojo == null) {
                getPojo = new DeviceStatusDto(deviceId, msg.getDasNodeId(), msg.getTerminalIp(), msg.isConnected());
                getPojo.setOnlineTime(msg.getOccurTime());
            } else {
                getPojo.setDeviceId(pojo.getDeviceId());
                getPojo.setNodeId(msg.getDasNodeId());
                getPojo.setTerminalIp(msg.getTerminalIp());
                getPojo.setConnected(msg.isConnected());
                if (msg.isConnected()) {
                    getPojo.setOfflineTime(pojo.getOfflineTime());
                    getPojo.setOnlineTime(msg.getOccurTime());
                } else {
                    getPojo.setOnlineTime(pojo.getOnlineTime());
                    getPojo.setOfflineTime(msg.getOccurTime());
                }
            }

            Map<String, Object> params = msg.getParams();
            Integer timeout = (Integer) params.get(Constant.ATTR_TIMEOUT);

            String tempDeviceId = msg.getSourceDeviceType() + CacheKeys.SEPARATOR + msg.getSourceDeviceId();
            //用于超时计算的key
            String tempKey = DmsCacheKeys.getCcsKeyForDeviceStatus(tempDeviceId);
            //状态不一样，则往上抛连接消息
            if (pojo == null || pojo.isConnected() != msg.isConnected()) {
                cache.putObject(ccsKey, JSON.toJSONString(getPojo));
                deviceMessageServiceImpl.processMsg(msg);
            }

            //如果上传的状态为连接，则延长超时时间
            if (msg.isConnected() && timeout != null) {
                cache.putObjectWithExpireTime(tempKey, tempDeviceId, timeout);
            } else {
                //如果上传的状态为下线，则直接删除
                cache.removeObject(tempKey);
            }
        } catch (Exception ee) {
            LOGGER.error("device status error", ee);
        }
    }

    @Override
    public DeviceStatusDto getDeviceStatus(String deviceId) {
        String ccsKey = DmsCacheKeys.getCcsKeyForDeviceStatus(deviceId);
        if (!cache.containsKey(ccsKey)) {
            return null;
        }

        return cache.getObject(ccsKey, DeviceStatusDto.class);
    }

    @Override
    public List<DeviceStatusDto> getDeviceStatusBatch(String[] deviceIds) {
        if (deviceIds == null || deviceIds.length <= 0) {
            return null;
        }

        List<DeviceStatusDto> deviceStatusList = Lists.newArrayList();
        Arrays.stream(deviceIds).forEach(t -> {
            DeviceStatusDto deviceStatus = getDeviceStatus(t);
            if (deviceStatus != null) {
                deviceStatusList.add(deviceStatus);
            }
        });

        return deviceStatusList;
    }
}
