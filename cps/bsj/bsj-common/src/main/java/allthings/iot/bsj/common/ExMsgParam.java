package allthings.iot.bsj.common;

/**
 * @author :  luhao
 * @FileName :  ExMsgParam
 * @CreateDate :  2018/1/4
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
public interface ExMsgParam {

    /**
     * 油耗
     */
    String YH = "YH";
    /**
     * 第一路温度
     */
    String WD1 = "WD1";
    /**
     * 第二路温度
     */
    String WD2 = "WD2";
    /**
     * 第三路温度
     */
    String WD3 = "WD3";
    /**
     * 第四路温度
     */
    String WD4 = "WD4";

    /**
     * 里程数据段
     */
    String LC = "LC";
    /**
     * 01到08是节点181数据[3020]
     */
    String CAN181 = "CAN181";
    /**
     * 09到10是节点281的数据[5020]
     */
    String CAN281 = "CAN281";

    /**
     * 11到18是381的数据[7020]
     */
    String CAN381 = "CAN381";

    /**
     * 19到20是节点481的数据[9020]
     */
    String CAN481 = "CAN481";
    /**
     * 固定10字节 表示 IC卡号
     */
    String ICCARDNO = "ICCARDNO";

    /**
     * 油位
     */
    String YW = "YW";

    /**
     * 油位比
     */
    String YWB = "YWB";

    /**
     * 油量
     */
    String YL = "YL";

    /**
     * 上传的文本信息
     */
    String WB = "WB";

    /**
     * 版本状态
     */
    String BBZT = "BBZT";

}
