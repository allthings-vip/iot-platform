package allthings.iot.bb808.common;

/**
 * @author :  luhao
 * @FileName :  MsgParamsWeight
 * @CreateDate :  2017/12/21
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
public interface MsgParamsWeight {
    /**
     * 报头
     */
    String HEAD = "head";

    /**
     * 消息码
     */
    String MSG_CODE = "msg_code";

    /**
     * 消息体
     */
    String MSG_BODY = "msg_body";

    /**
     * CRC
     */
    String CRC = "crc";

    /**
     * CRC校验是否通过
     */
    String PASS_CRC_CHECK = "passCrcCheck";

    /**
     * 时间戳
     */
    String ATTR_CS_WEIGHT_TIME = "subProtocolTime";

    /**
     * 毛重
     */
    String ATTR_CS_WEIGHT_GROSS_WEIGHT = "grossWeight";

    /**
     * 皮重
     */
    String ATTR_CS_WEIGHT_TARE_WEIGHT = "tareWeight";

    /**
     * 净重
     */
    String ATTR_CS_WEIGHT_NET_WEIGHT = "netWeight";

    /**
     * 累计重量
     */
    String ATTR_CS_WEIGHT_TOTAL_WEIGHT = "totalWeight";

    /**
     * 累计次数
     */
    String ATTR_CS_WEIGHT_TOTAL_COUNT = "totalCount";

    /**
     * 上行标定斜率
     */
    String ATTR_CS_WEIGHT_UP_SLOPE = "upSlope";

    /**
     * 上行标定截距
     */
    String ATTR_CS_WEIGHT_UP_INTERCEPT = "upIntercept";

    /**
     * 下行标定斜率
     */
    String ATTR_CS_WEIGHT_DOWN_SLOPE = "downSlope";

    /**
     * 下行标定截距
     */
    String ATTR_CS_WEIGHT_DOWN_INTERCEPT = "downIntercept";

    /**
     * CRC
     */
    String ATTR_CS_WEIGHT_CRC = "crc";

    /**
     * CRC校验是否通过
     */
    String ATTR_CS_WEIGHT_PASS_CRC_CHECK = "passCrcCheck";

    /**
     * 数据
     */
    String SENSOR_DATA = "sensorData";

    /**
     *   车辆id
     */
    String ATTR_CS_WEIGHT_CAR_ID = "carId";

}
