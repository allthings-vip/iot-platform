package allthings.iot.ktv.common;

/**
 * @author :  luhao
 * @FileName :  ColumnConstant
 * @CreateDate :  2018/3/28
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
public interface ColumnConstant {
    /**
     * 设备ID
     */
    String KTV_COL_DEVICE_ID = "deviceId";

    /**
     * 会员ID
     */
    String KTV_COL_PARTY_ID = "partyId";
    /**
     * 因子
     */
    String KTV_COL_FACTOR = "factor";

    /**
     * 值
     */
    String KTV_COL_VALUE = "value";

    /**
     * 时间戳
     */
    String KTV_COL_DATETIME = "datetime";

    /**
     * 里程
     */
    String KTV_COL_MILEAGE = "gpsMileage";

    /**
     * 结束油量
     */
    String KTV_COL_ENDOIL = "endOil";

    /**
     * 开始油量
     */
    String KTV_COL_STARTOIL = "startOil";

    /**
     * 里程
     */
    String DEVICE_MILEAGE = "mileage";

    /**
     * 油耗
     */
    String DEVICE_OIL = "oil_cost";

    /**
     * 运行时长
     */
    String DEVICE_RUN_TIME = "run_time";

    /**
     * 纬度
     */
    String POST_LATITUDE = "latitude";

    /**
     * 经度
     */
    String POST_LONGITUDE = "longitude";

    /**
     * 结束时间
     */
    String POST_END_TIME = "time_end";

    /**
     * 停车时长（秒）
     */
    String POST_POST_TIME = "time_post";

    /**
     * 停车地址
     */
    String POST_POSITION = "position";

    String ROW_KEY = "rowKey";
}
