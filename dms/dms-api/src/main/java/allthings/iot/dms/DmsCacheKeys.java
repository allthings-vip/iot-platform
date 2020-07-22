package allthings.iot.dms;

import allthings.iot.common.usual.CacheKeys;

/**
 * @author :  sylar
 * @FileName :  DmsCacheKeys
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
public class DmsCacheKeys extends CacheKeys {

    public static String getCcsKeyForDasStatus(String nodeId) {
        return getDmsKey(CCS, "nodeStatus", nodeId);
    }

    public static String getCcsKeyForDeviceStatus(String deviceId) {
        return getDmsKey(CCS, "deviceStatus", deviceId);
    }

}
