package allthings.iot.bb808.das;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import allthings.iot.bb808.common.FragSubPacketCache;
import allthings.iot.bb808.common.FragSubPacketCache.FragSubPacketSet;
import allthings.iot.bb808.common.MsgCode;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.common.PacketUtil;
import allthings.iot.bb808.das.packet.Packet0x0801;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.das.common.bean.MsgSender;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  MessageMonitorThread
 * @CreateDate :  2017/12/21
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
@Service(MessageMonitorThread.BEAN_NAME)
public class MessageMonitorThread implements Runnable {
    public final static String BEAN_NAME = "MessageMonitorThread";
    private static Logger logger = LoggerFactory.getLogger(MessageMonitorThread.class);

    /**
     * 线程轮询时间 30秒
     */
    private static final long TIME_MS_POLL = 30 * 1000;
    /**
     * 60秒
     */
    private static final long TIMEOUT_MS = 60 * 1000;

    @Autowired
    private MsgSender msgSender;

    @PostConstruct
    public void init() {
        startThread();
    }

    private void startThread() {
        Thread t = new Thread(this);
        t.setName(BEAN_NAME + "-Thread0");
        if (t.isDaemon()) {
            t.setDaemon(false);
        }

        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }

        // 启动线程
        t.start();
        logger.info("startThread [{}]", t.getName());
    }

    @Override
    public void run() {
        Map<String, Map<String, FragSubPacketSet>> fragMap = FragSubPacketCache.getFragMap();

        String deviceCode;
        String[] msgCodes = {MsgCode.MSG_MULTIMEDIA_UPLOAD};
        FragSubPacketSet set;
        try {
            while (true) {
                for (String msgCode : msgCodes) {
                    for (Map.Entry<String, Map<String, FragSubPacketSet>> entry : fragMap.entrySet()) {
                        deviceCode = entry.getKey();
                        set = entry.getValue().get(msgCode);
                        if (set == null) {
                            continue;
                        }

                        switch (msgCode) {
                            case MsgCode.MSG_MULTIMEDIA_UPLOAD:
                                monitor0x0801(deviceCode, set.getMessageTotalCount(), set.getLastDateTime());
                                break;

                            default:
                                break;
                        }
                    }
                }

                Thread.sleep(TIME_MS_POLL);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    /**
     * 如果在指定的超时时间后没有收到所有的分包，则下发0x8003指令。
     *
     * @param runningNo
     * @param deviceCode
     * @param idList
     */
    private void send0x8003(String deviceType, int runningNo, String deviceCode, List<Integer> idList) {
        DeviceMsg sendMsg = PacketUtil.getMsgOf0x8003(deviceType, deviceCode, runningNo, idList);
        msgSender.sendMsg(sendMsg);
    }


    /**
     * 如果在指定的超时时间后没有收到所有的分包，则下发0x8800指令。
     *
     * @param deviceCode
     * @param messageTotalCount
     * @param lastDateTime
     */
    private void monitor0x0801(String deviceCode, int messageTotalCount, Date lastDateTime) {
        String msgCode = MsgCode.MSG_MULTIMEDIA_UPLOAD;
        boolean needAck = isTimeout(lastDateTime) && !FragSubPacketCache.isAllReceived
                (deviceCode, msgCode, messageTotalCount);
        if (!needAck) {
            return;
        }

        byte[] bodyBytes = FragSubPacketCache.getWholeMessageBody(deviceCode, msgCode, messageTotalCount);
        Packet0x0801 packet0x0801 = new Packet0x0801();
        packet0x0801.setMessageBody(bodyBytes);
        packet0x0801.unpack(bodyBytes);

        // 向设备下发指令：0x8800
        Long multimediaDataId = packet0x0801.get(MsgParam.MULTIMEDIA_DATA_ID);
        List<Integer> idList = FragSubPacketCache.getNotExistPacketIdList(deviceCode, msgCode, messageTotalCount);

        send0x8800(DeviceTypes.DEVICE_TYPE_BB808, deviceCode, multimediaDataId, idList);
    }

    /**
     * 判断是否超时
     *
     * @param lastDateTime
     * @return
     */
    private boolean isTimeout(Date lastDateTime) {
        Date now = new Date();
        if (now.getTime() - lastDateTime.getTime() > TIMEOUT_MS) {
            return true;
        }

        return false;
    }

    /**
     * 发送多媒体数据上传应答
     *
     * @param deviceType
     * @param deviceCode
     * @param multimediaDataId
     * @param packetIdList
     */
    private void send0x8800(String deviceType, String deviceCode, Long multimediaDataId, List<Integer> packetIdList) {
        DeviceMsg sendMsg = PacketUtil.getMsgOf0x8800(deviceType, deviceCode, multimediaDataId, packetIdList);
        msgSender.sendMsg(sendMsg);
    }
}
