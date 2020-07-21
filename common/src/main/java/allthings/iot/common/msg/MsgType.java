package allthings.iot.common.msg;

import java.io.Serializable;

/**
 * Created by sylar on 16/5/25.
 */

/**
 * @author :  sylar
 * @FileName :  MqttConst
 * @CreateDate :  2017/11/08
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
public enum MsgType implements Serializable {
    /**
     * 未知
     */
    Unknown(-1),
    /**
     * 未定义
     */
    Undefine(0),
    /**
     * das 连接
     */
    DasConnection(1),
    /**
     * 设备连接
     */
    DeviceConnection(2),
    /**
     * 设备信息
     */
    DeviceInfo(11),
    /**
     * 设备报警
     */
    DeviceAlarm(12),
    /**
     * 设备事件
     */
    DeviceEvent(13),
    /**
     * 设备日志
     */
    DeviceLog(14),
    /**
     * 设备业务数据
     */
    DeviceData(15),
    /**
     * 设备在线升级
     */
    DeviceOta(16);

    private int value;

    MsgType(int value) {
        this.value = value;
    }

    public static MsgType valueOf(int value) {
        switch (value) {
            case 0:
                return Undefine;
            case 1:
                return DasConnection;
            case 2:
                return DeviceConnection;
            case 11:
                return DeviceInfo;
            case 12:
                return DeviceAlarm;
            case 13:
                return DeviceEvent;
            case 14:
                return DeviceLog;
            case 15:
                return DeviceData;
            case 16:
                return DeviceOta;
            default:
                return Unknown;
        }
    }

    public int getValue() {
        return value;
    }


}
