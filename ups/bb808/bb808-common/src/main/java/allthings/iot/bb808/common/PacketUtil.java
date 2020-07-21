package allthings.iot.bb808.common;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.IterableUtils;
import allthings.iot.common.msg.DeviceMsg;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  PacketUtil
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
public class PacketUtil {
    /**
     * 0x8800应答
     *
     * @param deviceType
     * @param deviceCode
     * @param multimediaDataId
     * @param packetIdList
     * @return
     */
    public static DeviceMsg getMsgOf0x8800(String deviceType, String deviceCode, Long multimediaDataId, List<Integer>
            packetIdList) {
        DeviceMsg sendMsg = DeviceMsg.newMsgFromCloud();
        sendMsg.setMsgCode(MsgCode.MSG_MULTIMEDIA_UPLOAD_RESPONSE);
        sendMsg.setTargetDeviceType(deviceType);
        sendMsg.setTargetDeviceId(deviceCode);

        Map<String, Object> sendParamMap = Maps.newHashMap();
        sendParamMap.put(MsgParam.MULTIMEDIA_DATA_ID, multimediaDataId);

        int retransPacketCount = 0;
        sendParamMap.put(MsgParam.RETRANS_PACKET_COUNT, retransPacketCount);

        if (!IterableUtils.isEmpty(packetIdList)) {
            retransPacketCount = packetIdList.size();
            sendParamMap.put(MsgParam.RETRANS_PACKET_COUNT, retransPacketCount);

            String ids = Joiner.on(";").join(packetIdList);
            sendParamMap.put(MsgParam.RETRANS_PACKET_ID_LIST, ids);
        }

        sendMsg.setParams(sendParamMap);
        return sendMsg;
    }

    /**
     * 补传分包请求
     *
     * @param deviceType
     * @param deviceCode
     * @param runningNo
     * @param idList
     * @return
     */
    public static DeviceMsg getMsgOf0x8003(String deviceType, String deviceCode, int runningNo, List<Integer> idList) {
        DeviceMsg sendMsg = DeviceMsg.newMsgFromCloud();
        sendMsg.setMsgCode(MsgCode.MSG_RESEND_PACKET);

        sendMsg.setTargetDeviceType(deviceType);
        sendMsg.setTargetDeviceId(deviceCode);
        Map<String, Object> sendParamMap = Maps.newHashMap();
        sendParamMap.put(MsgParam.ORIGIN_RUNNING_NO, runningNo);
        sendParamMap.put(MsgParam.RESEND_ID_LIST, idList);

        sendMsg.setParams(sendParamMap);
        return sendMsg;
    }
}
