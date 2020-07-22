package allthings.iot.dms.service;

import allthings.iot.common.msg.DasConnectionMsg;
import allthings.iot.dms.DmsCacheKeys;
import allthings.iot.dms.DmsConfig;
import allthings.iot.dms.IDasStatusService;
import allthings.iot.dms.dto.DasStatusDto;
import allthings.iot.dms.entity.IotDasStatus;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :  sylar
 * @FileName :  DasStatusServiceImpl
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
public class DasStatusServiceImpl implements IDmsMsgProcessor<DasConnectionMsg>, IDasStatusService {

    @Autowired
    DmsConfig dmsConfig;

    @Autowired
    private ICentralCacheService cache;

    @Override
    public void processMsg(DasConnectionMsg msg) {

        String nodeId = msg.getDasNodeId();
        String ccsKey = DmsCacheKeys.getCcsKeyForDasStatus(nodeId);

        if (msg.isConnected()) {
            IotDasStatus pojo = new IotDasStatus(nodeId);
            cache.putObject(ccsKey, JSON.toJSONString(pojo));
        } else {
            cache.removeObject(ccsKey);
        }
    }

    @Override
    public DasStatusDto getDasStatus(String nodeId) {
        String ccsKey = DmsCacheKeys.getCcsKeyForDasStatus(nodeId);
        if (!cache.containsKey(ccsKey)) {
            return null;
        }

        return cache.getObject(ccsKey, DasStatusDto.class);
    }

    public void updateDeviceConnection(String nodeId, String deviceId, boolean isConnected) {

        DasStatusDto pojo = getDasStatus(nodeId);
        if (pojo == null) {
            pojo = new DasStatusDto(nodeId);
        }

        if (isConnected) {
            pojo.addDeviceId(deviceId);
        } else {
            pojo.removeDeviceId(deviceId);
        }

        String ccsKey = DmsCacheKeys.getCcsKeyForDasStatus(nodeId);
        cache.putObject(ccsKey, JSON.toJSONString(pojo));
    }
}
