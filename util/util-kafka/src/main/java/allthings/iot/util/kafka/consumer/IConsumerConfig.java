package allthings.iot.util.kafka.consumer;


import allthings.iot.util.kafka.IServiceConfig;

/**
 * 消费者配置, bootstrap.servers , group.id 是必须项
 * 默认配置参见 @see Class#org.apache.kafka.clients.consumer.ConsumerConfig
 * Created by sylar on 2017/1/5.
 */
public interface IConsumerConfig extends IServiceConfig {

    /**
     * 消费组编码
     *
     * @return 消费组编码
     */
    String getGroupId();
}
