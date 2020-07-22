package allthings.iot.das.common.ota;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import allthings.iot.common.msg.IMsg;
import allthings.iot.das.common.bean.MsgSender;
import allthings.iot.das.common.event.OtaCompletedEvent;
import allthings.iot.das.common.event.OtaEachPacketResponseEvent;
import allthings.iot.das.common.event.OtaEvent;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  AbstractOtaWorker
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
public abstract class AbstractOtaWorker implements IOtaWorker,
        ApplicationListener<OtaEvent> {

    @Autowired
    OtaFactory otaFactory;
    @Autowired
    MsgSender msgSender;
    private Map<String, OtaTask> mapTask = Maps.newHashMap();

    /**
     * 构建Imsg
     *
     * @param deviceId       设备id
     * @param packetCount    包数量
     * @param packetIndex    包索引
     * @param eachPacketData 每个包的内容
     * @return Imsg
     */
    protected abstract IMsg buildEachPacketMsg(String deviceId, int packetCount, int packetIndex, byte[]
            eachPacketData);

    @PostConstruct
    private void init() {
        otaFactory.registWorker(getDeviceType(), this);
    }


    @Override
    public void startOtaTask(String deviceId, byte[] otaData) {
        if (mapTask.containsKey(deviceId)) {
            return;
        }

        OtaTask task = new OtaTask(this, deviceId, otaData);
        mapTask.put(deviceId, task);
        task.startTask();
    }

    @Override
    public void onApplicationEvent(OtaEvent event) {

        if (event instanceof OtaEachPacketResponseEvent) {
            OtaEachPacketResponseEvent oee = (OtaEachPacketResponseEvent) event;
            onOtaEachPacketResponseEvent(oee.getDeviceId(), oee.getCurrentPacketIndex());
        } else if (event instanceof OtaCompletedEvent) {
            OtaCompletedEvent oce = (OtaCompletedEvent) event;
            onOtaCompletedEvent(oce.getDeviceId());
        }
    }

    protected void onOtaEachPacketResponseEvent(String deviceId, int packetIndex) {
        if (getSendType() != OtaSendType.EachPacketResponse) {
            return;
        }

        if (!mapTask.containsKey(deviceId)) {
            return;
        }

        mapTask.get(deviceId).sendPacket(packetIndex + 1);
    }

    protected void onOtaCompletedEvent(String deviceId) {
        if (!mapTask.containsKey(deviceId)) {
            return;
        }

        mapTask.remove(deviceId);
    }

    public class OtaTask {
        private IOtaWorker worker;
        private String deviceId;
        private byte[] otaData;

        private int packetCount;
        private int frameLength;

        public OtaTask(IOtaWorker worker, String deviceId, byte[] otaData) {
            this.worker = worker;
            this.deviceId = deviceId;
            this.otaData = otaData;
            preCalc();
        }

        public void startTask() {

            switch (worker.getSendType()) {
                case EachPacketResponse:
                    sendPacket(0);
                    break;
                case LastPacketResponse:
                    for (int index = 0; index < packetCount; index++) {
                        sendPacket(index);
                    }
                    break;
                default:
                    break;
            }
        }

        public void sendPacket(int packetIndex) {
            byte[] packetData = getEachPacketData(packetIndex);
            IMsg msg = buildEachPacketMsg(deviceId, packetCount, packetIndex, packetData);
            msgSender.sendMsg(msg);
        }


        private void preCalc() {
            int totalLength = otaData.length;
            frameLength = worker.getMaxFrameLength();
            packetCount = (totalLength / frameLength + (totalLength % frameLength == 0 ? 0 : 1));
        }

        private byte[] getEachPacketData(int packetIndex) {
            if (packetIndex < 0 || packetIndex >= packetCount) {
                return null;
            }

            int len = frameLength;
            if (packetIndex == packetCount - 1) {
                len = otaData.length - packetIndex * frameLength;
            }

            byte[] frameData = new byte[len];
            System.arraycopy(otaData, packetIndex * frameLength, frameData, 0, len);
            return frameData;
        }

    }
}
