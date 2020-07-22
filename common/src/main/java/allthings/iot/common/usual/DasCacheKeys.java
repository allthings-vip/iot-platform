package allthings.iot.common.usual;

import allthings.iot.common.msg.MsgType;

/**
 * @author :  sylar
 * @FileName :  DasCacheKeys
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
@Deprecated
public class DasCacheKeys extends CacheKeys {
    protected final static String NODE = "node";
    protected final static String CLIENT = "client";
    protected final static String TOPIC = "topic";

    public static String getMqsKeyFromDmsToDas(String nodeId) {
        return getDasKey(DMS + SYMBOL_TO + DAS, NODE + SYMBOL_EQUAL + nodeId);
    }

    public static String getMqsKeyFromDasToDms() {
        return getDasKey(DAS + SYMBOL_TO + DMS);
    }

    public static String getMqsKeyByNodeIdAndMsgType(int nodeId, MsgType msgType) {
        return getDasKey(String.valueOf(nodeId), msgType.toString());
    }

    public static String getCcsKeyForByClientIdAnsMsgType(String clientId, MsgType msgType) {
        return getDasKey(CLIENT, clientId, msgType.toString());
    }
}
