package allthings.iot.ktv.common;

/**
 * @author :  luhao
 * @FileName :  Constant
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
public interface Constant {

    /**
     * 会员ID常量
     */
    String CONST_PARTY_ID = "-100";

    /**
     * hbase namespace
     */
    String IOT_HBASE_NAMESPACE = "IOT";

    /**
     * hbase table name
     */
    String KTV_DATA_TABLE = IOT_HBASE_NAMESPACE + ":" + "IOT_KTV_DATA_ONLINE";

    /**
     * hbase table column family
     */
    String KTV_COL_FAMILY = "gps";

    /**
     * 点数key
     */
    String POINT_COUNT_KEY = "POINT_COUNT_KEY";

    /**
     * 总数
     */
    String POINT_TOTAL_COUNT = "point_total_count";

    /**
     * 昨日数量
     */
    String POINT_YESTERDAY_COUNT = "point_yesterday_count";

    /**
     * 今日数量
     */
    String POINT_TODAY_COUNT = "point_today_count";

    /**
     * 里程油耗统计表名
     */
    String KTV_OIL_MILEAGE_TABLE = IOT_HBASE_NAMESPACE + ":" + "IOT_OIL_MILEAGE_DAILY";

    /**
     * 里程油耗列簇名
     */
    String KTV_OIL_MILEAGE_COL_FAMILY = "device";

    /**
     * 停车时间表名
     */
    String KTV_STOP_TIME_TABLE = IOT_HBASE_NAMESPACE + ":" + "IOT_POST_TIME";

    /**
     * 停车时间列簇名
     */
    String KTV_STOP_TIME_COL_PAMILY = "post";

}
