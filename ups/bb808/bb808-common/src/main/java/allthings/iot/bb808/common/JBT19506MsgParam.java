package allthings.iot.bb808.common;

/**
 * @author :  luhao
 * @FileName :  JBT19506MsgParam
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
public interface JBT19506MsgParam {
    /**
     *  00H - 采集记录仪执行标准版 记录仪执行标准年号后2位
     */
    String RECODER_STANDARD_YEAR = "recoderStandardYear";

    /**
     *  00H -采集记录仪执行标准版 修改单号
     */
    String RECODER_MODIFY_NUMBER = "recoderModifyNumber";

    /**
     *  01H/10H -采集当前驾驶人信息 机动车驾驶证号码
     */
    String DRIVER_LICENSE_NUMBER = "driverLicenseNumber";

    /**
     *  02H/03H/04H -采集记录仪的实时时间/采集累计行驶里程/记录仪脉冲系数  实时时间
     */
    String RECODER_TIME = "recoderTime";

    /**
     *  03H 采集累计行驶里程 初次安装时间
     */
    String INITIAL_INSTALLATION_TIME = "initialInstallationTime";

    /**
     *  03H 采集累计行驶里程 初始里程
     */
    String INITIAL_MILEAGE = "initialMileage";

    /**
     *  03H 采集累计行驶里程 累计行驶里程
     */
    String TOTAL_MILEAGE = "totalMileage";

    /**
     *  04H 记录仪脉冲系数 脉冲系数
     */
    String RECODER_PULSE = "recoderPulse";

    /**
     *  05H 采集车辆信息 车辆识别代号
     */
    String DEV_VIN = "deviceVin";

    /**
     *  05H 采集车辆信息 机动车号码号牌
     */
    String CAR_CODE = "carCode";

    /**
     *  05H 采集车辆信息 机动车号牌分类
     */
    String CAR_CODE_TYPE = "carCodeType";

    /**
     *  06H 采集记录仪状态信号配置信息 状态信号名称
     */
    String STATUS_SIGNAL = "statusSignal";

    /**
     *  06H 采集记录仪状态信号配置信息 配置信息列表
     */
    String CONFIG_LIST = "configList";

    /**
     *  07H 采集记录仪唯一性编号 生产厂CCC 认证代码
     */
    String CCC_AUTH_CODE = "cccAuthCode";

    /**
     *  07H 采集记录仪唯一性编号 认证产品型号
     */
    String RECODER_MODEL = "recoderModel";

    /**
     *  07H 采集记录仪唯一性编号 记录仪的生产日期
     */
    String RECODER_PRODUCTION_DATE = "recoderProductionDate";

    /**
     *  07H 采集记录仪唯一性编号 产品生产流水号
     */
    String RECODER_SERIAL_NO = "recoderSerialNo";

    /**
     *  通用 采集指定的数据记录命令帧数据块 开始时间
     */
    String START_TIME = "startTime";

    /**
     *  通用 采集指定的数据记录命令帧数据块 结束时间
     */
    String END_TIME = "endTime";

    /**
     *  通用 采集指定的数据记录命令帧数据块 最大数据块个数
     */
    String MAX_FRAME_COUNT = "maxFrameCount";

    /**
     *  08H/09H 采集指定的行驶速度记录 每分钟的记录列表
     */
    String RECODER_MIN_LIST = "recoderMinList";

    /**
     *  08H 采集指定的行驶速度记录 每秒的记录列表
     */
    String RECODER_SEC_LIST = "recoderSecSpeed";

    /**
     *  08H 采集指定的行驶速度记录 平均速度
     */
    String RECODER_AVG_SPEED = "recoderAvgSpeed";

    /**
     *  08H/10H 采集指定的行驶速度记录 信号量
     */
    String RECODER_SIGNAL = "recoderSignal";

    /**
     *  08H 采集指定的行驶速度记录 时间，精确到分钟
     */
    String MINUTE_TIME = "minuteTime";

    /**
     *  09H 采集指定的位置信息记录 时间，精确到小时
     */
    String HOUR_TIME = "hourTime";

    /**
     *  09H 采集指定的位置信息记录 每小时的记录列表
     */
    String RECODER_HOUR_LIST = "recoderHourList";

    /**
     *  09H 采集指定的位置信息记录 经度
     */
    String RECODER_LNG = "recoderLng";

    /**
     *  09H 采集指定的位置信息记录 纬度
     */
    String RECODER_LAT = "recoderLat";

    /**
     *  09H 采集指定的位置信息记录 海拔
     */
    String RECODER_ALTITUDE = "recoderAltitude";

    /**
     *  10H 采集指定的事故疑点记 速度
     */
    String RECODER_SPEED = "recoderSpeed";

    /**
     *  10H 采集指定的事故疑点记 事故记录
     */
    String RECODER_ACCIDENT = "recoderAccident";

    /**
     *  11H 采集指定的超时驾驶记 超时驾驶记录
     */
    String RECODER_OVERTIME = "recoderOvertime";

    /**
     *  11H 采集指定的超时驾驶记 起始位置记录
     */
    String RECODER_LOCATIONS = "recoderLocations";

    /**
     *  12H 采集指定的驾驶人身份记录 驾驶人身份记录
     */
    String RECODER_DRIVER = "recoderDriver";

    /**
     *  12H/13H/14H 采集指定的驾驶人身份记录 事件类型
     */
    String RECODER_EVENT_TYPE = "recoderEventType";

    /**
     *  13H 采集指定的记录仪外部供电记 供电记录
     */
    String RECODER_POWER = "recoderPower";

    /**
     *  14H 采集指定的记录仪参数修改记录
     */
    String RECODER_PARAM_UPDATE = "recoderParamUpdate";

    /**
     *   15H 采集指定的速度状态日志
     */
    String SPEED_STATUS_LOG = "speedStatusLog";

    /**
     * 15H 采集指定的速度状态日志 记录仪的速度状态
     */
    String RECODER_SPEED_STATUS = "recoderSpeedStatus";

    /**
     * 15H 采集指定的速度状态日志 参考速度
     */
    String RECODER_REFERENCE_SPEED = "recoderReferenceSpeed";

    /**
     *  16H 设置状态量配置信息 信号量名称列表
     */
    String STATUS_SIGNAL_LIST = "statusSignalList";


}
