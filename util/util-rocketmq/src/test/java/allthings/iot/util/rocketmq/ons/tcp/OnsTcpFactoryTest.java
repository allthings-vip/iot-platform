package allthings.iot.util.rocketmq.ons.tcp;

import allthings.iot.util.rocketmq.*;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import allthings.iot.util.rocketmq.ons.OnsServerConst;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpFactoryTest {
    final static String TOPIC = OnsConst.getFirst().getTopic();
    final static String ProducerId = OnsConst.getFirst().getProducerId();
    final static String ConsumerId = OnsConst.getFirst().getConsumerId();
    final static String AccessKey = OnsConst.getFirst().getAccessKey();
    final static String SecretKey = OnsConst.getFirst().getSecretKey();

    IFactory factory;

    @Before
    public void setUp() {
        factory = RocketMQUtil.createOnsTcpFactory(AccessKey, SecretKey, OnsServerConst.TCP_TEST);
    }

    @Test
    public void createProducer() throws Exception {
        IProducer producer = factory.createProducer(new IProducerConfig() {
            @Override
            public String getProducerId() {
                return ProducerId;
            }
        });

        RocketMsg msg = new RocketMsg(TOPIC);
        msg.setTags("TAGS_123");
        msg.setKeys("KEYS_123");
        msg.setContent(Util.pojo2String(new User()));

        System.out.println(producer.syncSend(msg));
        Thread.sleep(1000 * 5);
    }

    @Test
    public void createConsumer() throws Exception {
        IConsumer consumer = factory.createConsumer(new IConsumerConfig() {
            @Override
            public String getConsumerId() {
                return ConsumerId;
            }
        });

        consumer.subscribe(TOPIC, null, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> messages) {
                System.out.println(messages);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Thread.sleep(1000 * 30);
    }

}