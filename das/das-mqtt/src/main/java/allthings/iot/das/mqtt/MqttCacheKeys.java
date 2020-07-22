package allthings.iot.das.mqtt;

import allthings.iot.common.usual.DasCacheKeys;

/**
 * @author :  sylar
 * @FileName :  MqttCacheKeys
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class MqttCacheKeys extends DasCacheKeys {

    public static String getCcsKeyForTopicByNode(String nodeId) {
        return getDasKey("topicByNode", nodeId);
    }

    public static String getCcsKeyForNodesByTopic(String topic) {
        return getDasKey("nodesByTopic", topic);
    }
}
