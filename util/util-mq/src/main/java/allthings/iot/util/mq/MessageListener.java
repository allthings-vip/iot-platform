package allthings.iot.util.mq;

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
public interface MessageListener {

    /**
     * 成功时通知
     *
     * @param message 消息
     */
    void onSuccess(Message message);

    /**
     * 失败时通知
     *
     * @param t 异常信息
     */
    void onFailure(Throwable t);

}
