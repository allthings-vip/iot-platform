package allthings.iot.dms.service;

import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.dms.DmsCacheKeys;
import allthings.iot.dms.DmsConfig;
import allthings.iot.dms.IDeviceStatusService;
import allthings.iot.dms.dto.DeviceStatusDto;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

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
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service
public class DeviceStatusServiceImpl implements IDmsMsgProcessor<DeviceConnectionMsg>, IDeviceStatusService {

    @Autowired
    DmsConfig dmsConfig;

    @Autowired
    private ICentralCacheService cache;

    @Autowired
    DasStatusServiceImpl dasStatusServiceImpl;

    @Override
    public void processMsg(DeviceConnectionMsg msg) {
        String deviceId = msg.getSourceDeviceType() + msg.getSourceDeviceId();
        String ccsKey = DmsCacheKeys.getCcsKeyForDeviceStatus(deviceId);

        DeviceStatusDto pojo = cache.getObject(ccsKey, DeviceStatusDto.class);
        if (pojo == null) {
            pojo = new DeviceStatusDto(deviceId, msg.getDasNodeId(), msg.getTerminalIp(), msg.isConnected());
            pojo.setOnlineTime(msg.getOccurTime());
        } else {
            pojo.setNodeId(msg.getDasNodeId());
            pojo.setTerminalIp(msg.getTerminalIp());
            pojo.setConnected(msg.isConnected());
            if (msg.isConnected()) {
                pojo.setOnlineTime(msg.getOccurTime());
            } else {
                pojo.setOfflineTime(msg.getOccurTime());
            }
        }

        cache.putObject(ccsKey, JSON.toJSONString(pojo));

        dasStatusServiceImpl.updateDeviceConnection(msg.getDasNodeId(), deviceId, msg.isConnected());
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
