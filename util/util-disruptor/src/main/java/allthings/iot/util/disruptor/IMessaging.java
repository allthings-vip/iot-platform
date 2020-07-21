package allthings.iot.util.disruptor;

/**
 * @author :  sylar
 * @FileName :  IMessaging
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
public interface IMessaging {
    /**
     * 停止
     */
    void stop();

    /**
     * 发布
     *
     * @param value
     */
    void publish(Object value);

    /**
     * 获取buffer剩余的size
     *
     * @return
     */
    long getRemainBufferSize();
}
