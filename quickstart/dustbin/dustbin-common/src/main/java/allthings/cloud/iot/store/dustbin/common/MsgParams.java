package tf56.cloud.iot.store.dustbin.common;

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
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public interface MsgParams {

    /**
     * 回应码 1B 0-成功，1-失败
     */
    String RC = "RC";

    /**
     * 时间戳
     */
    String TIMESTAMP = "Timestamp";

    /**
     * 版本[1字节]
     */
    String VERSION = "Version";

    /**
     * RTC实时时间
     */
    String RTCTIME = "RtcTime";

    /**
     * 垃圾桶空余空间  探头1
     */
    String REMAIN1 = "Remain1";

    /**
     * 垃圾桶空余空间  探头3
     */
    String REMAIN2 = "Remain2";

    /**
     * 垃圾桶空余空间  探头3
     */
    String REMAIN3 = "Remain3";

    /**
     * 垃圾桶空余空间  探头4
     */
    String REMAIN4 = "Remain4";

    /**
     * 原始数据1
     */
    String ORIGINAL1 = "Original1";

    /**
     * 原始数据2
     */
    String ORIGINAL2 = "Original2";

    /**
     * 原始数据3
     */
    String ORIGINAL3 = "Original3";

    /**
     * 原始数据4
     */
    String ORIGINAL4 = "Original4";

    /**
     * 垃圾桶倾斜角度[1Byte]，单位度，范围0~180
     */
    String TILT = "Tilt";

    /**
     * 垃圾桶电池电压[2Byte],单位mV
     */
    String BATTERY = "Battery";


    /**
     * 垃圾桶温度[2Byte]，单位0.1摄氏度
     */
    String TEMPERATUE = "Temperatue";

    /**
     * 无线网络信号强度
     */
    String RSSI = "Rssi";

    /**
     * 垃圾桶满阈值[1Byte]，单位cm
     */
    String LIMIT1 = "Limit1";

    /**
     * 垃圾桶半满阈值[1Byte]，单位cm
     */
    String LIMIT2 = "Limit2";

    /**
     * 垃圾桶上报间隔[1Byte]，单位小时
     */
    String REPORTINTERVAL = "ReportInterval";

    /**
     * 垃圾桶云端地址长度[1Byte]
     */
    String CONNECTSTRINGLEN = "ConnectStringLen";

    /**
     * 垃圾桶云端地址[NByte],内容包括地址和端口，格式为 ip:port
     */
    String CONNECTSTRING = "ConnectString";

    /**
     * 移动报警[1Byte]，0:无报警，其他:有报警
     */
    String ALARMMOVE = "AlarmMove";

    /**
     * 传感器故障报警 探头1
     */
    String ALARMERROR1 = "AlarmError1";

    /**
     * 传感器故障报警 探头2
     */
    String ALARMERROR2 = "AlarmError2";

    /**
     * 传感器故障报警 探头3
     */
    String ALARMERROR3 = "AlarmError3";

    /**
     * LAC of service Tower
     */
    String LAC = "Lac";

    /**
     * Cell ID of service Tower
     */
    String CID = "Cid";

    /**
     * 定位信息
     */
    String LOC = "Loc";

}
