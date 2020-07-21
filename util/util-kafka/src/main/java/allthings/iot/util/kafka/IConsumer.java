package allthings.iot.util.kafka;


import allthings.iot.util.kafka.msg.IKafkaMsgListener;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Created by sylar on 2017/1/5.
 */
public interface IConsumer extends IService {

    /**
     * 使用指定的主题订阅消息(默认从当前位置)
     *
     * @param topics   主题列表
     * @param listener 收到消息时的回调
     */
    void subscribe(Collection<String> topics, IKafkaMsgListener listener);


    /**
     * 通过模式匹配订阅消息
     *
     * @param pattern  模式匹配
     * @param listener 收到消息时的回调
     */
    void subscribe(Pattern pattern, IKafkaMsgListener listener);


    /**
     * 定制化订阅
     *
     * @param topic     消息主题
     * @param partition 指定分区，默认从0开始
     * @param offset    指定读取位置（offset 相当于消息顺序存储的指针、游标，可以理解为消息ID）
     *                  <code>0<code/> 从最早的消息开始读取
     *                  <code>null</code> 从上一次位置开始读取
     *                  <code>N</code> 从offset=N 的位置开始读取
     * @param listener  收到消息时的回调
     */
    void subscribe(String topic, Integer partition, Long offset, IKafkaMsgListener listener);


}
