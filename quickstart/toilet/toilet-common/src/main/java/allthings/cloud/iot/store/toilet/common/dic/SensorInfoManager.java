package tf56.cloud.iot.store.toilet.common.dic;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import allthings.iot.common.pojo.DeviceGuid;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :  sylar
 * @FileName :  SensorInfoManager
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
public class SensorInfoManager {
    public final static int AREA_KEY_UNKNOWN = 0xFF;
    public final static int AREA_KEY_PUBLIC = 1;
    public final static int AREA_KEY_MALE = 2;
    public final static int AREA_KEY_FEMALE = 3;
    /**
     * 未标记区域
     */
    public final static String AREA_CHAR_UNKNOWN = "U";
    /**
     * 公共区域
     */
    public final static String AREA_CHAR_PUBLIC = "P";
    /**
     * 男厕
     */
    public final static String AREA_CHAR_MALE = "M";
    /**
     * 女厕
     */
    public final static String AREA_CHAR_FEMALE = "F";

    /**
     * 人流量
     */
    public final static String FACTOR_CODE_RL = "RL";
    /**
     * 用水量
     */
    public final static String FACTOR_CODE_SL = "SL";
    /**
     * 用电量
     */
    public final static String FACTOR_CODE_DL = "DL";
    /**
     * 谷电量
     */
    public final static String FACTOR_CODE_GDL = "GDL";
    /**
     * 温度
     */
    public final static String FACTOR_CODE_WD = "WD";
    /**
     * 温度
     */
    public final static String FACTOR_CODE_SD = "SD";
    /**
     * 风机
     */
    public final static String FACTOR_CODE_FJ = "FJ";
    /**
     * 除息
     */
    public final static String FACTOR_CODE_CC = "CC";
    /**
     * 臭气
     */
    public final static String FACTOR_CODE_CQ = "CQ";

    private static SensorInfoManager instance = new SensorInfoManager();
    Map<String, SensorInfo> sensorMap = Maps.newHashMap();

    private SensorInfoManager() {
    }

    synchronized public static SensorInfoManager getInstance() {
        return instance;
    }

    public static String generateFactorId(String templetId) {
        return templetId;
    }

    public static String generateFactorId(String templetId, int areaFlag) {
        return String.format("%s-%s", templetId, getAreaFlag(areaFlag));
    }

    public static String generateFactorId(String templetId, int areaFlag, int index) {
        return String.format("%s-%s-%s", templetId, getAreaFlag(areaFlag), index);
    }

    public static String getAreaFlag(int intAreaFlag) {
        switch (intAreaFlag) {
            case AREA_KEY_PUBLIC:
                return AREA_CHAR_PUBLIC;
            case AREA_KEY_MALE:
                return AREA_CHAR_MALE;
            case AREA_KEY_FEMALE:
                return AREA_CHAR_FEMALE;
            default:
                return AREA_CHAR_UNKNOWN;
        }
    }

    public void loadSensonInfos(SensorInfos sensorInfos) {
        sensorMap.clear();
        for (SensorInfo sensorInfo : sensorInfos.getSensorInfos()) {
            if (sensorInfo.checkValid()) {
                sensorMap.put(sensorInfo.getId(), sensorInfo);
            }
        }
    }

    public SensorInfo getSensorInfoById(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return null;
        }

        String deviceTypeId = null;
        if (id.length() == DeviceGuid.GUID_LENGTH) {
            deviceTypeId = DeviceGuid.fromString(id).getDeviceTypeId();
        } else if (id.length() == DeviceGuid.DEVICE_TYPE_LENGTH) {
            deviceTypeId = id;
        }

        if (deviceTypeId != null) {
            return sensorMap.get(deviceTypeId);
        } else {
            return null;
        }
    }

    public Map<String, Object> buildSensorDataMap(List<SensorDataWrap> wrapList) {

        //汇总所有因子
        List<FactorNode> nodes = Lists.newLinkedList();
        for (SensorDataWrap wrap : wrapList) {
            nodes.addAll(wrap.getNodes());
        }

        //分组,根据因子模板ID+区域
        Map<String, List<FactorNode>> map = Maps.newHashMap();
        List<FactorNode> sameNameNodes;
        for (FactorNode node : nodes) {
            String factorId = node.getFactorId();
            if (map.containsKey(factorId)) {
                sameNameNodes = map.get(factorId);
            } else {
                sameNameNodes = Lists.newLinkedList();
            }

            sameNameNodes.add(node);
            map.put(factorId, sameNameNodes);
        }

        Set<Map.Entry<String, List<FactorNode>>> set = map.entrySet();
        for (Map.Entry<String, List<FactorNode>> entity : set) {
            int count = entity.getValue().size();
            for (int i = 0; i < count; i++) {
                entity.getValue().get(i).setIndex(i + 1);
            }
        }

        //生成因子KV表
        Map<String, Object> res = Maps.newHashMap();
        for (FactorNode node : nodes) {
            String id = node.getFullFactorId();
            if (res.containsKey(id)) {
            } else {
                res.put(id, node.getValue());
            }
        }

        return res;
    }
}
