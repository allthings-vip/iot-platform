package allthings.iot.util.kafka.producer;

import com.google.common.base.Strings;
import allthings.iot.util.kafka.AbsService;
import allthings.iot.util.kafka.IProducer;
import allthings.iot.util.kafka.Util;
import allthings.iot.util.kafka.msg.KafkaMsg;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by sylar on 2017/3/9.
 */
public class SimpleProcuder extends AbsService implements IProducer {

    protected IProducerConfig config;
    protected KafkaProducer<String, String> producer;

    public SimpleProcuder(IProducerConfig config) {
        this.config = config;

        Util.checkBrokerListConfig(config.getBootstrapServers());
    }

    public IProducerConfig getConfig() {
        return config;
    }

    public KafkaProducer<String, String> getProducer() {
        return producer;
    }

    @Override
    protected void onStart() {
        Properties properties = new Properties();
        properties.putAll(config);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        if (!Strings.isNullOrEmpty(config.getClientId())) {
            properties.put(ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
        }

        producer = new KafkaProducer<>(properties);
    }

    @Override
    protected void onStop() {
        if (producer != null) {
            producer.close();
        }
    }


    @Override
    public Future<RecordMetadata> send(KafkaMsg msg) throws Exception {
        return producer.send(KafkaMsg.msgToRecord(msg));
    }
}
