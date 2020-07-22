package allthings.iot.das.common.ota;

/**
 * @author :  sylar
 * @FileName :  IOtaWorker
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
public interface IOtaWorker {

    /**
     * 取设备类型
     *
     * @return 设备类型
     */
    String getDeviceType();

    /**
     * 取发送类型
     *
     * @return 发送类型
     * @see OtaSendType
     */
    OtaSendType getSendType();

    /**
     * 最大帧长度
     *
     * @return 长度
     */
    int getMaxFrameLength();

    /**
     * 启动在线升级任务
     *
     * @param deviceId 设备id
     * @param otaData  固件内容
     */
    void startOtaTask(String deviceId, byte[] otaData);
}
