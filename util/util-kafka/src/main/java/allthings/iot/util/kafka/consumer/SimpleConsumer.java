package allthings.iot.util.kafka.consumer;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import allthings.iot.util.kafka.AbsService;
import allthings.iot.util.kafka.IConsumer;
import allthings.iot.util.kafka.Util;
import allthings.iot.util.kafka.consumer.loop.PullLoop;
import allthings.iot.util.kafka.msg.IKafkaMsgListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by sylar on 2017/3/9.
 */
public class SimpleConsumer extends AbsService implements IConsumer {
    protected IConsumerConfig config;
    protected KafkaConsumer<String, String> consumer;
    private PullLoop loop;

    public SimpleConsumer(IConsumerConfig config) {
        this.config = config;

        Util.checkBrokerListConfig(config.getBootstrapServers());
        Preconditions.checkNotNull(config.getGroupId(), "consumer's group.id can not be null");
    }

    public IConsumerConfig getConfig() {
        return config;
    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

    @Override
    protected void onStart() {
        Properties properties = new Properties();
        properties.putAll(config);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());

        /* 强制由业务层来确认 */
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);  //是否自动确认offset

        if (!Strings.isNullOrEmpty(config.getClientId())) {
            properties.put(ConsumerConfig.CLIENT_ID_CONFIG, config.getClientId());
        }

        consumer = new KafkaConsumer<>(properties);
    }

    @Override
    protected void onStop() {
        if (loop != null) {
            loop.stop();
            loop = null;
        }
    }

    @Override
    public void subscribe(Collection<String> topics, IKafkaMsgListener listener) {
        Preconditions.checkNotNull(listener, "listener is null");

        try {
            checkBeforeSubscribe();
            Preconditions.checkNotNull(topics, "topics is null");
            Preconditions.checkState(topics.size() > 0, "topics is empty");

            consumer.subscribe(topics);

            loop = new PullLoop(consumer, listener);
            loop.start();
        } catch (Exception e) {
            listener.onFaild(e);
        }
    }

    @Override
    public void subscribe(Pattern pattern, IKafkaMsgListener listener) {
        Preconditions.checkNotNull(listener, "listener is null");

        try {
            checkBeforeSubscribe();
            Preconditions.checkNotNull(pattern, "pattern is null");

            consumer.subscribe(pattern, new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

                }
            });

            loop = new PullLoop(consumer, listener);
            loop.start();

        } catch (Exception e) {
            listener.onFaild(e);
        }
    }

    @Override
    public void subscribe(String topic, Integer partition, Long offset, IKafkaMsgListener listener) {
        Preconditions.checkNotNull(listener, "listener is null");

        try {
            checkBeforeSubscribe();
            Preconditions.checkNotNull(topic, "topics is null");

            if (partition == null) {
                partition = 0;
            }

            Preconditions.checkState(checkPartitionValid(topic, partition),
                    String.format("invalid partition. topic:%s  partition:%s", topic, partition));

            TopicPartition topicPartition = new TopicPartition(topic, partition);
            Collection<TopicPartition> topicPartitions = Collections.singletonList(topicPartition);

            //seek 之前必须 assign 分区或订阅
            consumer.assign(topicPartitions);

            if (offset == null) {
                consumer.seekToEnd(new ArrayList<>());
            } else if (offset == 0L) {
                consumer.seekToBeginning(new ArrayList<>());
            } else {
                Long end = consumer.endOffsets(topicPartitions).get(topicPartition);
                Preconditions.checkState(end != null && end > offset,
                        String.format("invalid offset,topic:%s  partition:%s offset:%s", topic, partition, offset));

                consumer.seek(topicPartition, offset);
            }

            loop = new PullLoop(consumer, listener);
            loop.start();
        } catch (Exception e) {
            listener.onFaild(e);
        }
    }

    void checkBeforeSubscribe() {
        Preconditions.checkNotNull(consumer, "consumer is null, can not subscribe");
        consumer.unsubscribe();
    }

    boolean checkPartitionValid(String topic, Integer partition) {
        for (PartitionInfo partitionInfo : consumer.partitionsFor(topic)) {
            if (partitionInfo.partition() == partition) {
                return true;
            }
        }
        return false;
    }

}
