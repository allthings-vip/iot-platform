package allthings.iot.util.redis;

/**
 * @author :  sylar
 * @FileName :  IMessageQueueService
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
public interface IMessageQueueService {

    /**
     * 是否包含topic
     *
     * @param queueName
     * @return
     */
    boolean containsQueue(String queueName);

    /**
     * 队列剩余长度
     *
     * @param queueName
     * @return
     */
    long getRemainSize(String queueName);

    /**
     * 发送消息
     *
     * @param queueName
     * @param msg
     * @param <Msg>
     */
    <Msg> void sendMessage(String queueName, Msg msg);

    /**
     * 接受消息
     *
     * @param queueName
     * @param clazz
     * @param <Msg>
     * @return
     */
    <Msg> Msg receiveMessage(String queueName, Class<Msg> clazz);

}
