package tf56.cloud.iot.store.toilet.common.protolcol;

/**
 * @author :  sylar
 * @FileName :  MsgParams
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
public interface MsgParams {

    /**
     * 回应码 1B 0-成功，1-失败
     */
    String RC = "RC";

    /**
     * 总包号
     */
    String OTAPACKETCOUNT = "OtaPacketCount";

    /**
     * 当前包号
     */
    String OTACURRENTPACKETINDEX = "OtaCurrentPacketIndex";

    /**
     * 包内容
     */
    String OTAPACKETDATA = "OtaPacketData";

    /**
     * 云端推送内容
     */
    String PUSHCONTENT = "PushContent";

    /**
     * 上报间隔
     */
    String REPORTINTERVAL = "ReportInterval";

    /**
     * SSID String 根据WifiSsid_Len确定长度
     */
    String WIFISSID = "WifiSsid";

    /**
     * Wifi pasword String 根据WifiPwd_Len确定长度
     */
    String WIFIPWD = "WifiPwd";

    /**
     * 设备个数 1B
     */
    String DEVICENUM = "DeviceNum";

    /**
     * 实时时间
     */
    String TIME = "Time";

    /**
     * 考勤卡ID
     */
    String RFID = "Rfid";

    /**
     * 传感器列表
     */
    String SENSONS = "Sensons";

    /**
     * 设备Guid
     */
    String GUID = "Guid";

    /**
     * 开关风机阈值
     */
    String TOILETPARAMFAN = "ToiletParamFan";

    /**
     * 喷除臭剂阈值
     */
    String TOILETPARAMDEODORANT = "ToiletParamDeodorant";

}
