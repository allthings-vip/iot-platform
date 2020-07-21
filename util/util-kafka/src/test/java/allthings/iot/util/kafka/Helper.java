package allthings.iot.util.kafka;

import com.google.common.collect.Lists;
import allthings.iot.util.kafka.consumer.IConsumerConfig;
import allthings.iot.util.kafka.consumer.SimpleConsumer;
import allthings.iot.util.kafka.consumer.SimpleConsumerConfig;
import allthings.iot.util.kafka.model.Num2;
import allthings.iot.util.kafka.msg.IKafkaMsgListener;
import allthings.iot.util.kafka.msg.KafkaMsg;
import allthings.iot.util.kafka.producer.SimpleProcuder;
import allthings.iot.util.kafka.producer.SimpleProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

/**
 * Created by sylar on 2017/3/10.
 */
public class Helper {

    final static String BrokerList = Const.BrokerList;
    final static int MsgCountPerProducer = 5000;

    public static void clusterProduce(String topic, int producerCount) throws Exception {
        for (int i = 1; i <= producerCount; i++) {
            String clientId = "PID_" + i;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        produce(topic, clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void produce(String topic, String clientId) throws Exception {

        IProducer producer = new SimpleProcuder(new SimpleProducerConfig(BrokerList, clientId));
        producer.start();

        for (int i = 0; i < MsgCountPerProducer; i++) {
            KafkaMsg msg = KafkaMsg.buildMsg(topic, new Num2(clientId, i));
            Future<RecordMetadata> future = producer.send(msg);
            RecordMetadata metadata = future.get();
            System.out.println(String.format("producer:%s\tindex:%s\toffset:%s ", clientId, i, metadata.offset()));
            Thread.sleep(10 + new SecureRandom().nextInt(10));
        }

        producer.stop();
    }

    public static void broadcastConsume(String topic, String groupId, int groupCount) throws Exception {
        for (int i = 1; i <= groupCount; i++) {
            String gid = groupId + "_" + i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        consumeByTopic(topic, "CID_" + new SecureRandom().nextInt(10), gid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void clusterConsume(String topic, String groupId, int consumerCount) throws Exception {
        for (int i = 1; i <= consumerCount; i++) {
            String cid = "CID_" + i;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        consumeByTopic(topic, cid, groupId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void consumeByTopic(String topic, String clientId, String groupId) throws Exception {
        IConsumerConfig config = new SimpleConsumerConfig(BrokerList, clientId, groupId);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);

        IConsumer consumer = new SimpleConsumer(config);

        consumer.start();
        List<String> topics = Lists.newArrayList(topic);

        consumer.subscribe(topics, new MsgListener() {
            @Override
            protected void onShowMsg(KafkaMsg msg) {
                System.out.println(String.format("group:%s\tclientId:%s", groupId, clientId));
                super.onShowMsg(msg);
            }
        });

        Thread.sleep(1000 * 10);
        consumer.stop();
    }

    public static void consumeByPartten(Pattern patten, String clientId, String groupId) throws Exception {
        IConsumerConfig config = new SimpleConsumerConfig(BrokerList, clientId, groupId);
        IConsumer consumer = new SimpleConsumer(config);

        consumer.start();

        consumer.subscribe(patten, new MsgListener() {
            @Override
            protected void onShowMsg(KafkaMsg msg) {
                System.out.println(String.format("group:%s\tclientId:%s", groupId, clientId));
                super.onShowMsg(msg);
            }
        });

        Thread.sleep(1000 * 30);
        consumer.stop();
    }

    public static void consumeByCustom(String topic, Integer partition, Long offset, String clientId, String groupId)
            throws Exception {
        IConsumerConfig config = new SimpleConsumerConfig(BrokerList, clientId, groupId);
        IConsumer consumer = new SimpleConsumer(config);

        consumer.start();

        consumer.subscribe(topic, partition, offset, new MsgListener() {
            @Override
            protected void onShowMsg(KafkaMsg msg) {
                System.out.println(String.format("group:%s\tclientId:%s", groupId, clientId));
                super.onShowMsg(msg);
            }
        });

        Thread.sleep(1000 * 30);

        consumer.stop();
    }

    static class MsgListener implements IKafkaMsgListener {
        @Override
        public boolean onProcessAndConfirm(ConsumerRecords<String, String> records) {
            //TODO 修改为原始记录。自己业务处理；本身就是需要处理的，无需在前面进行封装；
            if (records != null) {
                records.forEach(record ->
                        {
                            // 这个其实也不需要，直接取record里面的内容进行处理即可
                            onShowMsg(KafkaMsg.recordToMsg(record));
                        }
                );
            }
            return true;
        }

        @Override
        public void onFaild(Throwable throwable) {
            System.out.println(throwable);
        }

        protected void onShowMsg(KafkaMsg msg) {
            System.out.println(msg);
        }
    }
}
