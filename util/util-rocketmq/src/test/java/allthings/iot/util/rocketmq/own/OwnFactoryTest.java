package allthings.iot.util.rocketmq.own;

import allthings.iot.util.rocketmq.*;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by sylar on 2017/1/7.
 */
public class OwnFactoryTest {

    final static String NameServerAddr = "127.0.0.1:9876";
    final static String TOPIC = OnsConst.getFirst().getTopic();
    final static String ProducerId = OnsConst.getFirst().getProducerId();
    final static String ConsumerId = OnsConst.getFirst().getConsumerId();

    IFactory factory;

    @Before
    public void setUp() {
        factory = RocketMQUtil.createOwnFactory(NameServerAddr);
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
        msg.setTags("TAGS_123_own");
        msg.setKeys("KEYS_123_own");
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