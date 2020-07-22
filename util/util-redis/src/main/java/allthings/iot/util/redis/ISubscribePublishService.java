package allthings.iot.util.redis;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  ISubscribePublishService
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
public interface ISubscribePublishService {
    /**
     * 发布消息
     *
     * @param topic
     * @param msg
     */
    void publishMessage(String topic, Object msg);

    /**
     * 订阅消息
     *
     * @param messageListener
     * @param topics
     */
    void subscribeMessage(AbstractMessageListener messageListener, List<String> topics);

    /**
     * 取消订阅
     *
     * @param messageListener
     * @param topics
     */
    void unsubscribeMessage(AbstractMessageListener messageListener, List<String> topics);
}
