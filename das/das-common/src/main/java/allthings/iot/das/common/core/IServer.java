package allthings.iot.das.common.core;

/**
 * @author :  sylar
 * @FileName :  IServer
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
public interface IServer {
    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 重启
     */
    void restart();

    /**
     * 是否在运行
     *
     * @return 运行状态
     */
    boolean isRunning();
}
