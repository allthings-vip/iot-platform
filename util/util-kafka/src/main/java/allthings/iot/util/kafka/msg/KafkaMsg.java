package allthings.iot.util.kafka.msg;

import com.alibaba.fastjson.annotation.JSONField;
import allthings.iot.util.kafka.Util;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;


/**
 * Created by sylar on 2017/1/6.
 */
public class KafkaMsg implements Serializable {

    public static ProducerRecord<String, String> msgToRecord(KafkaMsg msg) {
        return new ProducerRecord<>(
                msg.topic,
                msg.partition,
                msg.timestamp,
                msg.key,
                msg.value);
    }


    public static KafkaMsg recordToMsg(ConsumerRecord<String, String> record) {
        return new KafkaMsg(
                record.topic(),
                record.partition(),
                record.offset(),
                record.timestamp(),
                record.key(),
                record.value()
        );
    }

    public static KafkaMsg buildMsg(String topic, Object value) {
        return new KafkaMsg(topic, null, value);
    }

    public static KafkaMsg buildMsg(String topic, String key, Object value) {
        return new KafkaMsg(topic, key, value);
    }


    private String topic;
    private Integer partition = null;
    private Long timestamp = System.currentTimeMillis();
    private String key;
    private String value;
    private Long offset;

    protected KafkaMsg(String topic, String key, Object value) {
        this(topic, null, null, System.currentTimeMillis(), key, Util.pojo2String(value));
    }

    protected KafkaMsg(String topic, Integer partition, Long offset, Long timestamp, String key, String value) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
    }


    @JSONField(serialize = false, deserialize = false)
    public <T> T getPojo(Class<T> clazz) {
        return Util.string2Pojo(value, clazz);
    }


    @Override
    public String toString() {
        return Util.pojo2String(this);
    }


    public String getTopic() {
        return topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Long getOffset() {
        return offset;
    }
}
