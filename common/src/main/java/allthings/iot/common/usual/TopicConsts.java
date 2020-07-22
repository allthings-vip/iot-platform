package allthings.iot.common.usual;

/**
 * @author :  sylar
 * @FileName :  TopicConsts
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
public interface TopicConsts {

    /**
     * 上行设备消息主题，从DAS到DMS
     */
    String DAS_TO_DMS = "IOT-DasToDms";

    /**
     * 下行设备消息主题，从DMS到DAS
     */
    String DMS_TO_DAS = "IOT-DmsToDas";

    /**
     * 下行设备消息主题，从DMS到AP
     */
    String DMS_TO_APS = "IOT-DmsToAps";

    /**
     * 下行消息，从Aps到Aep
     */
    String APS_TO_AEP = "IOT-ApsToAep";

    /**
     * 上行消息，从Aep到Aps
     */
    String AEP_TO_APS = "IOT-AepToAps";
}
