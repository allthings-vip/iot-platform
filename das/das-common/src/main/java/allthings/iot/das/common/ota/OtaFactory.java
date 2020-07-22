package allthings.iot.das.common.ota;

import com.google.common.collect.Maps;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import allthings.iot.common.msg.DeviceOtaMsg;
import allthings.iot.das.common.event.OtaNewTaskEvent;

import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  OtaFactory
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
@Component
public class OtaFactory implements ApplicationListener<OtaNewTaskEvent> {

    private Map<String, IOtaWorker> mapWorker = Maps.newHashMap();

    public void registWorker(String deviceType, IOtaWorker worker) {
        mapWorker.put(deviceType, worker);
    }

    @Override
    public void onApplicationEvent(OtaNewTaskEvent event) {
        DeviceOtaMsg msg = event.getMsg();
        String deviceType = msg.getTargetDeviceType();
        if (!mapWorker.containsKey(deviceType)) {
            return;
        }

        IOtaWorker worker = mapWorker.get(deviceType);
        String deviceId = msg.getPlatformCode() == null ? msg.getTargetDeviceId() : msg.getPlatformCode();
        byte[] otaData = msg.getOtaData();
        worker.startOtaTask(deviceId, otaData);
    }

}
