package allthings.iot.util.mq;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  IConsumer
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
public interface IConsumer extends IClient {


    /**
     * 设置广播模式
     * RocketMQ 广播消费模式是通过 setMessageModel 显示设置
     * Kafka 广播消费是通过 consumer group id 来区分，不同组即可广播， 组内则是集群消费
     *
     * @param isBroadcasting 是否启用广播，默认是集群模式
     */
    void setBroadcasting(boolean isBroadcasting);

    /**
     * 订阅
     *
     * @param topics          主题列表
     * @param messageListener 消息监听器
     */
    void subscribe(List<String> topics, MessageListener messageListener);


    /**
     * 取消订阅
     */
    void unsubscribe();
}
