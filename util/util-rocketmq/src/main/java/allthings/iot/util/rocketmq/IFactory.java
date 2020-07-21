package allthings.iot.util.rocketmq;

/**
 * Created by sylar on 2017/1/6.
 */
public interface IFactory {
    IProducer createProducer(IProducerConfig config);

    IConsumer createConsumer(IConsumerConfig config);
}
