package allthings.iot.util.rocketmq;


import allthings.iot.util.rocketmq.msg.RocketMsg;

/**
 * Created by sylar on 2017/1/5.
 */
public interface IProducer {

    /**
     * 同步发送
     * 只有不抛异常，即发送成功
     *
     * @param msg 消息
     * @return 发送完成的回应
     * @throws Exception
     */
    Object syncSend(RocketMsg msg) throws Exception;

    /**
     * 关闭producer
     */
    void shutdown();
}
