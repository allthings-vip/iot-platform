package allthings.iot.dms.service;

import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.dms.DmsConfig;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  DeviceDataService
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
public class DeviceDataService implements IDmsMsgProcessor<DeviceDataMsg> {
    private static final Logger LOG = LoggerFactory.getLogger(DeviceDataService.class);

    @Autowired
    DmsConfig dmsConfig;

    //ISimpleDataService sds;

    @PostConstruct
    private void init() {
       // sds = dmsConfig.getSds();
    }

    @Override
    public void processMsg(DeviceDataMsg msg) {

        String deviceType = msg.getSourceDeviceType();
        String deviceId = msg.getSourceDeviceId();
        String fullId = deviceType + deviceId;

        if (Strings.isNullOrEmpty(deviceId)) {
            LOG.warn("invalid deviceId");
            return;
        }

        Map<String, Object> map = msg.getParams();
        if (map == null || map.isEmpty()) {
            LOG.warn("params of DeviceDataMsg is empty");
            return;
        }

        /*DeviceFactorsData factorsData = new DeviceFactorsData();
        factorsData.setDeviceCode(fullId);

        DeviceFactorData factorData;
        Set<Map.Entry<String, Object>> set = map.entrySet();
        String key;
        Object value;

        for (Map.Entry<String, Object> entry : set) {
            key = entry.getKey();
            value = entry.getValue();
            if (Strings.isNullOrEmpty(key) || value == null) {
                LOG.warn("invalid factorCode or factorValue");
                continue;
            }

            factorData = new DeviceFactorData();
            factorData.setDeviceType(deviceType);
            factorData.setDeviceId(fullId);

            factorData.setAcquisitionDatetime(msg.getTimestamp());
            factorData.setDeviceFactorCode(key);
            factorData.setDeviceFactorValue(value);

            factorsData.getDeviceFactorDataList().add(factorData);
        }

        sds.saveDeviceFactorsData(factorsData);*/
    }
}
