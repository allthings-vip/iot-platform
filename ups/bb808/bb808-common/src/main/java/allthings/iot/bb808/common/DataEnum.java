package allthings.iot.bb808.common;

/**
 * @author :  luhao
 * @FileName :  DataEnum
 * @CreateDate :  2017/12/21
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
public enum DataEnum {
    DEVICE_LOGIN,
    // 设备升级开始到结束过程中，相关的一系列状态
    DEVICE_UPGRADE,

    VEHICLE_OBD,
    VEHICLE_GPS,
    //位置信息查询回复
    VEHICLE_GPS_RES,
    VEHICLE_OIL_WATER,
    VEHICLE_OIL,
    VEHICLE_WATER,
    VEHICLE_RFID,
    VEHICLE_WEIGHT,
    //杭州电子秤，0x5D传感器数据
    VEHICLE_WEIGHT_HZ_SENSOR_DATA,
    // 驾驶员身份信息（从业资格证IC信息）
    VEHICLE_DRIVER_IC,
    // 多媒体数据，如图片、声音
    VEHICLE_MULTIMEDIA,
    // 终端参数
    VEHICLE_SETTING,
    // 终端属性
    VEHICLE_ATTR,
    //事件上报
    VEHICLE_EVENT_UPLOAD,
    //提问应答
    VEHICLE_QES_ANSWER,
    //信息点播
    VEHICLE_INFO_SUB,
    //终端控制应答
    VEHICLE_CONTROL_RES,
    //CAN总线数据
    VEHICLE_CAN_DATA,
    //行车记录仪数据
    VEHICLE_RECODER_DATA,
    //电子运单数据
    VEHICLE_WAYBILL_DATA,
    //存储多媒体数据检索应答
    VEHICLE_MULTIMEDIA_SEARCH_RES,
    //压缩数据上报
    VEHICLE_GZIP_DATA,
    //多媒体事件上报
    VEHICLE_MULTIMEDIA_EVENT,
    //终端RSA
    VEHICLE_RSA_DATA,
    //升级结果
    VEHICLE_UPGRADE_RESULT,
    //终端通用应答
    VEHICLE_GENERATE_RES,
    // 电池电量
    STAFF_BATTERY,
    // 计步数
    STAFF_STEPS,
    // 信号强度
    STAFF_SIGNAL,
    // 穿戴检测
    STAFF_WEAR_DETECTION,
    // 心率
    STAFF_HEART_RATE,
    // GPS数据
    STAFF_GPS,
    // GSM制式下的基站 + Wifi数据
    STAFF_LBS_WIFI,
    // SOS
    STAFF_SOS,
    // 签到，打卡
    STAFF_CLOCK,
    // 文字通知已读
    STAFF_TEXT_READ,
    // 语音通知已读
    STAFF_AUDIO_READ,
    // 拍照等多媒体数据
    STAFF_MULTI_MEDIA_DATA,
    //位置信息上报
    STAFF_POSITION_UPLOAD,
    // 地磅称重数据 - 新增/删除
    WEIGH,
    WEIGH_MULTIMEDIA,

    //809
    //0x9002，从链路连接应答
    ACCESS_ACCOUNT,
    RX_VALID,
    //0X9004,从链路注销应答
    RX_DISCONN,
    //用于809sub的data服务发送命令
    BB809_CMD,

    //HJT212
    //污染数据
    POLLUTION_DATA,

    //DTU 渗滤液
    SLY_DATA,
    //恒星通 OIL
    HXT_OIL,
}