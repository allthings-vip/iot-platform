package allthings.iot.bb808.common;

/**
 * @author :  luhao
 * @FileName :  SubProtocolCode
 * @CreateDate :  2017/12/21
 * @Description : 定义具体透传数据对应的协议。【注意】最好保证对应存在类 "Packet" + 值
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface SubProtocolCode {
    /**
     * 称重 - 杭州自研
     */
    String WEIGHT = "Weight";

    /**
     * 称重 - 深圳汉德
     */
    String WEIGHT_HAND = "WeightHand";

    /**
     * 刷卡 - UHF
     */
    String RFID = "Rfid";

    /**
     * 水电 - 杭州自研
     */
    String WATER_AND_ELECTRICITY = "WaterAndElectricity";

    /**
     * 液体 - 富从
     */
    String OIL_OR_WATER = "OilOrWater";

    /**
     * 液体 - 南测
     */
    String LIQUID_NAN_CE = "LiquidNanCe";

    /**
     * 科隆
     */
    String KE_LONG = "KeLong";

}
