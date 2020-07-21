package allthings.iot.dms;

import com.google.common.base.Joiner;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DmsTopics
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
public class DmsTopics {
    /**
     * dms:{deviceType}:{deviecId}
     */
    public final static String TOPIC_FORMAT = "dms:%s:%s";

    public static String getTopicByAll() {
        return String.format(TOPIC_FORMAT, "*", "*");
    }

    public static String getTopicByDeviceType(String deviceType) {
        return String.format(TOPIC_FORMAT, deviceType, "*");
    }

    public static String getTopicByDeviceTypes(List<String> deviceTypes) {
        String str = genGlobStr(deviceTypes);
        return String.format(TOPIC_FORMAT, str, "*");
    }

    public static String getTopicByDeviceId(String deviceId) {
        return String.format(TOPIC_FORMAT, "*", deviceId);
    }

    public static String getTopicByDeviceIds(List<String> deviceIds) {
        String str = genGlobStr(deviceIds);
        return String.format(TOPIC_FORMAT, "*", str);
    }

    public static String getTopicWhenPublish(String deviceType, String deviceId) {
        return String.format(TOPIC_FORMAT, deviceType, deviceId);
    }

    private static String genGlobStr(List<String> items) {
        if (items == null || items.size() == 0) {
            return "*";
        } else {
            return "{" + Joiner.on(",").skipNulls().join(items) + "}";
        }
    }
}
