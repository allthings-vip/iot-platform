package allthings.iot.util.kafka;


import allthings.iot.util.kafka.msg.KafkaMsg;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

/**
 * Created by sylar on 2017/1/5.
 */
public interface IProducer extends IService {

    Future<RecordMetadata> send(KafkaMsg msg) throws Exception;
}
