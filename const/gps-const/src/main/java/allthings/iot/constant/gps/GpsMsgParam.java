package allthings.iot.constant.gps;

/**
 * @author :  luhao
 * @FileName :  GpsMsgParam
 * @CreateDate :  2018/1/16
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
public interface GpsMsgParam {

    /**
     * GPS信息 - GPS北京日期时间
     */
    String ATTR_GPS_DATETIME = "gps_datetime";

    /**
     * GPS信息 - GPS定位卫星数量
     */
    String ATTR_GPS_NUM = "gps_num";

    /**
     * GPS信息 - GPS纬度 latitude
     */
    String ATTR_GPS_LATITUDE = "gps_latitude";

    /**
     * GPS信息 - GPS经度longitude
     */
    String ATTR_GPS_LONGITUDE = "gps_longitude";

    /**
     * GPS信息 - GPS速度（单位：km/h）
     */
    String ATTR_GPS_SPEED = "gps_speed";

    /**
     * GPS信息 - GPS方向direction （单位：度）
     */
    String ATTR_GPS_DIRECTION = "gps_direction";

    /**
     * GPS信息 - GPS海拔高度Altitude （单位：米）
     */
    String ATTR_GPS_ALTITUDE = "gps_altitude";

    /**
     * GPS信息 - 报警标志
     */
    String ATTR_GPS_ALARM = "gps_alarm";

    /**
     * GPS信息 - 状态
     */
    String ATTR_GPS_STATUS = "gps_status";

    /**
     * 0x33 定时监控数据上传 - 当前的时速
     */
    String ATTR_CURRENT_SPEED = "ct_speed";

    /**
     * 0x33 定时监控数据上传 - GPS统计总里程数(4byte)
     */
    String ATTR_GPS_MILEAGE = "gps_mileage";


    /**
     * 0x22 定位数据 - GPS - GPS定位已否
     */
    String GPS_VALID = "gps_valid";


    /**
     * 为List<java.util.Map>对象。兼容批量多个GPS点位
     */
    String DATA_CONTENT = "dataContent";

    /**
     * 0x33 定时监控数据上传 - 里程数(F2)(单位0.01KM, 0.01公里)
     */
    String ATTR_MILEAGE = "mileage";


    /**
     * 0x33 定时监控数据上传 - 开关量第1路
     */
    String ATTR_SWITCH0 = "switch0";

    /**
     * 0x33 定时监控数据上传 - 开关量第2路
     */
    String ATTR_SWITCH1 = "switch1";

    /**
     * 0x33 定时监控数据上传 - 开关量第3路
     */
    String ATTR_SWITCH2 = "switch2";

    /**
     * 0x33 定时监控数据上传 - 开关量第4路
     */
    String ATTR_SWITCH3 = "switch3";

    /**
     * 0x33 定时监控数据上传 - 模拟量0
     */
    String ATTR_ANALOG0 = "analog0";

    /**
     * 0x33 定时监控数据上传 - 模拟量1
     */
    String ATTR_ANALOG1 = "analog1";

    /**
     * 0x33 定时监控数据上传 - 模拟量2
     */
    String ATTR_ANALOG2 = "analog2";

    /**
     * 0x33 定时监控数据上传 - 模拟量3
     */
    String ATTR_ANALOG3 = "analog3";

    /**
     * 0x31 终端设备状态 - 麦克、音响（RS232串口1）
     */
    String ATTR_MICROPHONE = "microphone";

    /**
     * 0x31 终端设备状态 - OBD接口（RS232串口2）
     */
    String ATTR_OBD = "obd";

    /**
     * 0x31 终端设备状态 - 摄像头1（RS232串口3）
     */
    String ATTR_CAMERA = "camera";
    /**
     * 0x31 终端设备状态 - RS232串口4
     */
    String ATTR_COM4 = "com4";
    /**
     * 0x31 终端设备状态 - RS232串口5
     */
    String ATTR_COM5 = "com5";
    /**
     * 0x31 终端设备状态 - RS232串口6
     */
    String ATTR_COM6 = "com6";

    /**
     * 0x31 终端设备状态 - 车辆点火状态。置位为已点火。
     */
    String ATTR_IGNITION = "ignition";

    /**
     * 0x31 终端设备状态 - 常火，置位为有。此标识只有在车辆点火状态为熄火时才有效
     */
    String ATTR_FIRE = "fire";


    /**
     * 0x31 终端设备状态 - SD卡是否正常。 置位为正常
     */
    String ATTR_SD = "sd";

    /**
     * 油量类型：超时波、模拟量、真实油量（如多少升）
     */
    String ATTR_OIL_TYPE = "oilType";

    /**
     * 0x33 定时监控数据上传 - 油量信息
     */
    String ATTR_OIL = "oil";

    /**
     * 0x33 定时监控数据上传 - 油量信息单位
     */
    String ATTR_OIL_UNIT = "oil_unit";

    /**
     * 0x33 定时监控数据上传 - 是否新值 指示油量信息(F8)是否为全新值
     */
    String ATTR_OIL_NEW = "oil_new";
    /**
     * 0x33 定时监控数据上传 - 计算次数 油量信息计算次数。
     */
    String ATTR_OIL_CALC_TIME = "oil_calc_time";

    /**
     * 0x33 定时监控数据上传 - 温度
     */
    String ATTR_TEMPERATURE = "temperature";

    /**
     * 0x33 定时监控数据上传 - 油量临时值 Temporary value of oil quantity
     */
    String ATTR_TEMP_OIL = "temp_oil";

    /**
     * 0x33 定时监控数据上传 - 油量临时值的单位
     */
    String ATTR_TEMP_OIL_UNIT = "temp_oil_unit";
    /**
     * 车辆状态 - 车门加锁否
     */
    String DOOR_LOCK = "doorLock";


    /**
     * gps天线状态 3-GPS正常 1-GPS天线开路
     */
    String ATTR_ANTENNA_STATUS = "gps_antenna_status";
    /**
     * 电源状态 3-正常 2-主电源掉电 1-主电源压过低
     */
    String ATTR_POWER_STATUS = "power_status";
    /**
     * 登签id(1) 0-7
     */
    String ATTR_SIGN_UP_ID_1 = "signup_id_1";
    /**
     * 登签状态 D1=1 没有登签 D1=0 已登签
     */
    String ATTR_SIGN_UP_STATUS = "signup_status";

    /**
     * 油路状态 D2=1 油路正常 D2=0 D2=0 油路断开
     */
    String ATTR_OIL_STATUS = "oil_status";

    /**
     * D7=0 劫警报警 D7=1 正常
     */
    String ATTR_ALARM_ROBBERY = "alarm_robbery";
    /**
     * 超速报警  D6=0 超速报警 D6=1 正常
     */
    String ATTR_ALARM_SPEEDING = "alarm_speeding";
    /**
     * D5=0 停车超长报警 D5=1 正常
     */
    String ATTR_ALARM_PARKING = "alarm_parking";
    /**
     * D1=0 GPRS已上线、D1=1 GPRS没有上线
     */
    String ATTR_GPRS_ONLINE_STATUS = "gpsr_online_status";
    /**
     * D7=0 GPRS注册、D7=1 GPRS未注册
     */
    String ATTR_GPRS_REGISTER_STATUS = "gprs_register_status";
    /**
     * D6=0 中心不需下发21应答指令、D6=1中心应下发21应答指令 表示中心是否对该条数据给予21应答指令,TCP方式时是不需应答的
     */
    String ATTR_CODE_DOWN_STATUS = "code_down_status";
    /**
     * D5=0 UDP通讯方式、D5=1 TCP通讯方式   表示该条数的通讯方式
     */
    String ATTR_COMMUNICATION_METHOD = "communication_method";
    /**
     * D4 D3 D2 D1 D0 ：CSQ信号状态0-31
     */
    String ATTR_CSQ_STATUS = "csq_status";
    /**
     * W W：ACC开时定时发送时间间隔 2个字节 单位秒,如30秒，表示为0x001E
     */
    String ATTR_ACC_INTERVAL = "acc_interval";
    /**
     * E： 停车超时时间   1个字节 单位分,如10分，表示为0x0A
     */
    String ATTR_PARKING_TIMEOUT = "parking_timeout";
    /**
     * 超速设置门阀   1个字节 单位公里/小时,如100公里/小时，表示为0x64
     */
    String ATTR_OVERSPEED_VALVE = "overspeed_valve";
    /**
     * T； 电子围栏设置个数 1个字节 HEX表示，0~0xFF
     */
    String ATTR_FENCE_NUM = "fence_num";

    /**
     * Y： 登签ID（2） 与登签ID（1）组成登签ID号
     */
    String ATTR_SIGN_UP_ID_2 = "signup_id_2";

    /**
     * GPS信息 GPS详细地址
     */
    String ATTR_GPS_ADDRESS = "address";

    /**
     * GPS信息 GPS城市
     */
    String ATTR_GPS_CITY = "city";

    /**
     * GPS信息 GPS省份
     */
    String ATTR_GPS_PROVINCE = "province";

    /**
     * GPS信息 GPS区
     */
    String ATTR_GPS_REGION = "region";

    /**
     * GPS信息 - BD09 百度 GPS纬度 latitude
     */
    String ATTR_GPS_BD09_LATITUDE = "gps_bd09_latitude";

    /**
     * GPS信息 - BD09 百度 GPS经度longitude
     */
    String ATTR_GPS_BD09_LONGITUDE = "gps_bd09_longitude";

    /**
     * GPS信息 - GCJ02 国测局 GPS纬度 latitude
     */
    String ATTR_GPS_GCJ02_LATITUDE = "gps_gcj02_latitude";

    /**
     * GPS信息 - GCJ02 国测局 GPS经度longitude
     */
    String ATTR_GPS_GCJ02_LONGITUDE = "gps_gcj02_longitude";

    /**
     * 深度睡眠状态
     */
    String DEEP_SLEEP_STATUS = "deepSleepStatus";

    /**
     * 睡眠状态
     */
    String SLEEP_STATUS = "sleepStatus";

    /**
     * 无线信号强度
     */
    String WIRELESS_SIGNAL_INTENSITY = "wirelessSignalIntensity";

    /**
     * 国家编码
     */
    String MCC = "mcc";

    /**
     * 运营商编码MNC
     */
    String MNC = "mnc";

    /**
     * 位置区（大区）编号LAC
     */
    String LAC = "lac";

    /**
     * 小区编号（CELLID）
     */
    String CELL_ID = "cellId";

    /**
     * 接收信号等级
     */
    String RX_LEV = "rxLev";

    String TA = "ta";

    String LA_NUM = "laNum";

    /**
     * gps数据上报频率
     */
    String GPS_REPORT_RATE = "gps_report_rate";

}
