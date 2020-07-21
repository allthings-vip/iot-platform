package allthings.iot.util.rocketmq.ons.mqtt;

import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import allthings.iot.util.rocketmq.ons.OnsServerConst;
import org.junit.Before;
import org.junit.Test;
import allthings.iot.util.rocketmq.*;

import java.util.List;

/**
 * Created by sylar on 2017/1/7.
 */
public class OnsMqttFactoryTest {

    final static String TOPIC = OnsConst.getFirst().getTopic();
    final static String ProducerId = OnsConst.getFirst().getProducerId();
    final static String ConsumerId = OnsConst.getFirst().getConsumerId();
    final static String AccessKey = OnsConst.getFirst().getAccessKey();
    final static String SecretKey = OnsConst.getFirst().getSecretKey();
    final static String ClientId1 = "GID_VORTEX_TEST@@@111";
    final static String ClientId2 = "GID_VORTEX_TEST@@@222";

//    IFactory factory;

    @Before
    public void setUp() {

    }


    @Test
    public void createProducer() throws Exception {
        IFactory factory = RocketMQUtil.createOnsMqttFactory(AccessKey, SecretKey, OnsServerConst.MQTT_TEST, ClientId1);
        IProducer producer = factory.createProducer(new IProducerConfig() {
            @Override
            public String getProducerId() {
                return ProducerId;
            }
        });

        RocketMsg msg = new RocketMsg(TOPIC);
        msg.setContent(Util.pojo2String(new User()));

        //mqtt 方式时， tags keys 无效
        msg.setTags("TAGS_mqtt");
        msg.setKeys("KEYS_mqtt");

        //如果 targetClientId != null, 则相当于点对点发送，  否则相当于广播发送
        msg.setTargetClientId(ClientId2);

        System.out.println(producer.syncSend(msg));
        Thread.sleep(1000 * 1);
    }

    @Test
    public void createConsumer() throws Exception {
        IFactory factory = RocketMQUtil.createOnsMqttFactory(AccessKey, SecretKey, OnsServerConst.MQTT_TEST, ClientId2);
        IConsumer consumer = factory.createConsumer(new IConsumerConfig() {
            @Override
            public String getConsumerId() {
                return ConsumerId;
            }
        });

        consumer.subscribe(TOPIC, new String[]{"/notice", "/p2p/" + ClientId2}, new IRocketMsgListener() {
            @Override
            public void onSuccess(List<RocketMsg> messages) {
                System.out.println("收到消息，开始处理。。。");
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Thread.sleep(1000 * 60 * 10);
        consumer.unsubscribe();
    }

}