package tf56.cloud.iot.store.toilet.common.protolcol;

/**
 * @author :  sylar
 * @FileName :  MsgCodes
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
public interface MsgCodes {

    /**
     * 云端必须解析并处理的指令
     */

    /**
     * 设备上线通知
     */
    int ONLINE_NOTIFY = 32;

    /**
     * 时间同步
     */
    int SYNC_TIME = 65;
    int SYNC_TIME_RES = 66;

    /**
     * OTA
     */
    int OTA_SEND = 67;
    int OTA_SEND_RES = 68;
    int OTA_SUCCESS_NOTIFY = 69;

    /**
     * 传感器列表变动上报
     */
    int SENSORS_CHANGED_NOTIFY = 50;

    /**
     * 传感器数据上报
     */
    int REPORT_SENSOR_DATA = 57;
    int REPORT_SENSOR_DATA_RES = 58;

    /**
     * 设备事件上报
     */
    int EVENT_NOTIFY = 72;

    /**
     * 云端推送通知信息
     */
    int CLOUD_PUSH = 73;

    /**
     * 考勤数据上报
     */
    int REPORT_RFID_DATA = 130;
    int REPORT_RFID_DATA_RES = 131;

    /**
     * 以下为云端可能用到的指令
     */

    /**
     * 设置路由信息
     */
    int SET_ROUTE = 35;
    int SET_ROUTE_RES = 36;

    /**
     * 获取路由信息
     */
    int GET_ROUTE = 37;
    int GET_ROUTE_RES = 38;

    /**
     * 获取传感器列表
     */
    int GET_SENSORS = 48;
    int GET_SENSORS_RES = 49;

    /**
     * 删除传感器
     */
    int DELETE_SENSOR = 53;
    int DELETE_SENSOR_RES = 54;

    /**
     * 获取传感器数据
     */
    int GET_SENSOR_DATA = 55;
    int GET_SENSOR_DATA_RES = 56;

    /**
     * 设置传感器数据上报间隔
     */
    int SET_REPORT_INTERVAL = 59;
    int SET_REPORT_INTERVAL_RES = 60;

    /**
     * 获取传感器数据上报间隔
     */
    int GET_REPORT_INTERVAL = 61;
    int GET_REPORT_INTERVAL_RES = 62;

    /**
     * 设置DeviceNumber
     */
    int SET_DEVICE_NUM = 63;
    int SET_DEVICE_NUM_RES = 64;

    /**
     * 公厕业务指令
     */

    /**
     * 公厕智能除臭阈值设置
     */
    int SET_TOILET_PARAM = 128;
    int SET_TOILET_PARAM_RES = 129;

}
