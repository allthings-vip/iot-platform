package allthings.iot.bb808.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  FragSubPacketCache
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
public class FragSubPacketCache {

    /**
     * 分包情况下，用来保存所有分包的数据结构。
     * Map<Integer, FragSubPacket>:	key - 包序号， value - 相应的分包数据
     * Map<String, Map<Integer, FragSubPacket>>:	key - 指令编号，如0801
     * Map<String, Map<String, Map<Integer, FragSubPacket>>>：	key - 设备编号
     */
    private static Map<String, Map<String, FragSubPacketSet>> fragMap = Maps.newConcurrentMap();

    /**
     * 按照一级维度（设备）、二级维度（ 指令）、三级维度（分包序号），存储分包数据。
     */
    public static void cacheSubPacket(String deviceCode, String msgCode, Integer messageTotalCount, Integer
            messageIndex, byte[] byteMessageBody) {
        Date now = new Date();

        Map<String, FragSubPacketSet> deviceMap = fragMap.get(deviceCode);
        if (deviceMap == null) {
            deviceMap = Maps.newHashMap();
            fragMap.put(deviceCode, deviceMap);
        }

        FragSubPacketSet set = deviceMap.get(msgCode);
        if (set == null) {
            set = new FragSubPacketSet();
        }
        set.setMessageTotalCount(messageTotalCount);
        set.setLastDateTime(now);
        deviceMap.put(msgCode, set);

        Map<Integer, FragSubPacket> packetMap = set.getPacketMap();
        if (packetMap == null) {
            packetMap = Maps.newHashMap();
        }

        FragSubPacket fragSubPacket = new FragSubPacket(now, byteMessageBody);
        packetMap.put(messageIndex, fragSubPacket);
    }

    public static Map<Integer, FragSubPacket> getPacketMap(String deviceCode, String msgCode) {
        Map<String, FragSubPacketSet> deviceMap = fragMap.get(deviceCode);
        if (MapUtils.isEmpty(deviceMap)) {
            return null;
        }

        return deviceMap.get(msgCode).getPacketMap();
    }

    /**
     * 是否 所有分包数据已经接收到
     *
     * @param deviceCode
     * @param msgCode
     * @param messageTotalCount
     * @param msgCode
     * @return
     */
    public static boolean isAllReceived(String deviceCode, String msgCode, Integer messageTotalCount) {
        Map<Integer, FragSubPacket> packetMap = getPacketMap(deviceCode, msgCode);
        for (int i = 1; i <= messageTotalCount; i++) {
            if (packetMap.get(i) == null) {
                return false;
            }
        }

        return true;
    }

    public static byte[] getWholeMessageBody(String deviceCode, String msgCode, int messageTotalCount) {
        byte[] body = null;

        Map<Integer, FragSubPacket> packetMap = getPacketMap(deviceCode, msgCode);

        FragSubPacket fragSubPacket = null;
        for (int i = 1; i <= messageTotalCount; i++) {
            fragSubPacket = packetMap.get(i);
            if (fragSubPacket == null) {
                continue;
            }

            body = ArrayUtils.addAll(body, fragSubPacket.getData());
        }

        return body;
    }

    public static List<Integer> getNotExistPacketIdList(String deviceCode, String msgCode, int messageTotalCount) {
        List<Integer> list = Lists.newArrayList();

        Map<Integer, FragSubPacket> packetMap = getPacketMap(deviceCode, msgCode);

        FragSubPacket fragSubPacket = null;
        for (int i = 1; i <= messageTotalCount; i++) {
            fragSubPacket = packetMap.get(i);
            if (fragSubPacket == null) {
                list.add(i);
            }
        }

        return list;
    }

    public static void clearSubPacketCache(String deviceCode, String msgCode) {
        Map<String, FragSubPacketSet> deviceMap = fragMap.get(deviceCode);
        if (MapUtils.isEmpty(deviceMap)){
            return;
        }

        FragSubPacketSet fragSubPacketSet = deviceMap.get(msgCode);
        if (fragSubPacketSet == null) {
            return;
        }

        int messageTotalCount = fragSubPacketSet.getMessageTotalCount();
        Map<Integer, FragSubPacket> packetMap = fragSubPacketSet.getPacketMap();

        if (packetMap.size() < messageTotalCount) {
            return;
        }

        packetMap.clear();

        // 释放
        fragSubPacketSet.setPacketMap(null);
        fragSubPacketSet.setMessageTotalCount(null);
        deviceMap.remove(msgCode);
        if (deviceMap.isEmpty()) {
            fragMap.remove(deviceCode);
        }
    }

    /**
     * 获取第一个分包的流水号
     *
     * @param deviceCode
     * @param msgCode
     * @return
     */
    public static int getFirstPacketRunningNo(String deviceCode, String msgCode) {
        Map<Integer, FragSubPacket> packetMap = getPacketMap(deviceCode, msgCode);

        FragSubPacket fragSubPacket = null;

        fragSubPacket = packetMap.get(1);
        if (fragSubPacket == null) {
//                logger.warn("device [{}] msgCode[{}] has no sub packet [{}]", deviceCode, msgCode, i);
            return 0;
        }

        byte[] data = fragSubPacket.getData();
        ByteBuffer buf = ByteBuffer.wrap(data);
        buf.order(ByteOrder.BIG_ENDIAN);
        int runningNo = buf.getShort(10) & (short) 0xffff;
        return runningNo;
    }

    public static Map<String, Map<String, FragSubPacketSet>> getFragMap() {
        return fragMap;
    }

    public static void setFragMap(Map<String, Map<String, FragSubPacketSet>> fragMap) {
        FragSubPacketCache.fragMap = fragMap;
    }

    public static class FragSubPacket {
        private Date receivedDate;
        private byte[] data;

        public FragSubPacket(Date receivedDate, byte[] data) {
            super();
            this.receivedDate = receivedDate;
            this.data = data;
        }

        public Date getReceivedDate() {
            return receivedDate;
        }

        public void setReceivedDate(Date receivedDate) {
            this.receivedDate = receivedDate;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

    public static class FragSubPacketSet {
        private Integer messageTotalCount;
        private Date lastDateTime;
        private Map<Integer, FragSubPacket> packetMap = new HashMap<Integer, FragSubPacket>();

        public Integer getMessageTotalCount() {
            return messageTotalCount;
        }

        public void setMessageTotalCount(Integer messageTotalCount) {
            this.messageTotalCount = messageTotalCount;
        }

        public Date getLastDateTime() {
            return lastDateTime;
        }

        public void setLastDateTime(Date lastDateTime) {
            this.lastDateTime = lastDateTime;
        }

        public Map<Integer, FragSubPacket> getPacketMap() {
            return packetMap;
        }

        public void setPacketMap(Map<Integer, FragSubPacket> packetMap) {
            this.packetMap = packetMap;
        }
    }
}
