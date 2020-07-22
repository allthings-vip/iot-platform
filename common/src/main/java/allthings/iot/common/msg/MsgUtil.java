package allthings.iot.common.msg;

/**
 * @author :  sylar
 * @FileName :  MsgUtil
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
public class MsgUtil {

    public static Class classOf(MsgType msgType) {
        switch (msgType) {
            case Undefine:
                return DeviceMsg.class;
            case DasConnection:
                return DasConnectionMsg.class;
            case DeviceConnection:
                return DeviceConnectionMsg.class;
            case DeviceAlarm:
                return DeviceAlarmMsg.class;
            case DeviceData:
                return DeviceDataMsg.class;
            case DeviceEvent:
                return DeviceEventMsg.class;
            case DeviceInfo:
                return DeviceInfoMsg.class;
            case DeviceLog:
                return DeviceLogMsg.class;
            case DeviceOta:
                return DeviceOtaMsg.class;
            default:
                return UnknownMsg.class;
        }
    }
}
