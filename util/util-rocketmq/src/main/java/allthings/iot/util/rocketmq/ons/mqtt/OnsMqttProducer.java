package allthings.iot.util.rocketmq.ons.mqtt;

import com.google.common.base.Preconditions;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.IProducerConfig;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttProducer extends AbsClient implements IProducer {

    IProducerConfig config;

    public OnsMqttProducer(OnsMqttFactory factory, IProducerConfig config) {
        super(factory);
        this.config = config;

        init();
    }

    void init() {
        try {
            open();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object syncSend(RocketMsg msg) throws Exception {
        Preconditions.checkState(client.isConnected(), "mqtt client is not connected");
        MqttMessage message = new MqttMessage(msg.getContent().getBytes());
        message.setQos(0);

        System.out.println("topic:" + msg.getMqttTopic());
        System.out.println("content:" + msg.getContent());
        send(msg.getMqttTopic(), message);
        return null;
    }

    @Override
    public void shutdown() {

    }
}
