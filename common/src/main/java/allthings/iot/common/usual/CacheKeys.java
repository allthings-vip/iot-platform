package allthings.iot.common.usual;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class CacheKeys {
    public final static String SEPARATOR = ":";
    protected final static String CCS = "ccs";
    protected final static String MQS = "mqs";
    protected final static String SPS = "sps";
    protected final static String DAS = "das";
    protected final static String DMS = "dms";
    protected final static String ANS = "ans";
    protected final static String SDS = "sds";
    protected final static String APS = "aps";

    protected final static String SYMBOL_EQUAL = "=";
    protected final static String SYMBOL_FROM = "<-";
    protected final static String SYMBOL_TO = "->";
    protected final static String SYMBOL_TWO_WAY = "<->";


    public static String getCcsKey(String... params) {
        return generateKeyBySystem(CCS, params);
    }

    public static String getMqsKey(String... params) {
        return generateKeyBySystem(MQS, params);
    }

    public static String getSpsKey(String... params) {
        return generateKeyBySystem(SPS, params);
    }

    public static String getDasKey(String... params) {
        return generateKeyBySystem(DAS, params);
    }

    public static String getDmsKey(String... params) {
        return generateKeyBySystem(DMS, params);
    }

    public static String getAnsKey(String... params) {
        return generateKeyBySystem(ANS, params);
    }

    public static String getSdsKey(String... params) {
        return generateKeyBySystem(SDS, params);
    }

    public static String getApsKey(String... params) {
        return generateKeyBySystem(APS, params);
    }

    public static String generateKeyBySystem(String sysCode, String... params) {
        return generateKey(sysCode, generateKey(params));
    }

    public static String generateKey(String... params) {
        Preconditions.checkNotNull(params);
        Preconditions.checkState(params.length > 0, "params is empty");
        return Joiner.on(SEPARATOR).skipNulls().join(params);
    }


}
