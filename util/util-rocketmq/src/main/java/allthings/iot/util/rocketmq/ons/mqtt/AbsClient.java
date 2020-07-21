package allthings.iot.util.rocketmq.ons.mqtt;

import com.google.common.eventbus.EventBus;
import allthings.iot.util.rocketmq.Util;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by sylar on 2017/1/5.
 */
public abstract class AbsClient {

    protected OnsMqttFactory factory;
    protected MqttClient client;
    protected MqttConnectOptions connOpts;
    protected String mqttUser, mqttPwd;
    protected EventBus eventBus = new EventBus(getClass().getName());

    public AbsClient(OnsMqttFactory factory) {
        this.factory = factory;

        try {
            /**
             * 将accessKey作为MQTT的username
             * 计算签名，将签名作为MQTT的password。
             * 签名的计算方法，参考工具类MacSignature，第一个参数是ClientID的前半部分，即GroupID
             * 第二个参数阿里云的SecretKey
             */
            mqttUser = factory.getAccessKey();
            mqttPwd = MacSignature.macSignature(factory.getClientId().split("@@@")[0], factory.getSecretKey());
            initClient(factory.getServerEndpoint(), factory.getClientId(), mqttUser, mqttPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initClient(String broker, String clientId, String userName, String password) throws Exception {
        MemoryPersistence persistence = new MemoryPersistence();
        client = new MqttClient(broker, clientId, persistence);
        connOpts = new MqttConnectOptions();
        connOpts.setUserName(userName);
        connOpts.setServerURIs(new String[]{broker});
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(false);
        connOpts.setKeepAliveInterval(100);
        client.setCallback(mqttCallback);
    }

    protected void open() throws MqttException {
        if (client != null && !client.isConnected()) {
            client.connect(connOpts);
        }
    }

    protected void close() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
    }

    protected void send(String topic, MqttMessage msg) throws MqttException {
        client.publish(topic, msg);
    }


    protected void onSendSuccess(IMqttDeliveryToken token) {
        System.out.println("消息发送成功.");
        eventBus.post(new DeliveryCompleteEvent(token));
    }

    protected void onReceived(RocketMsg msg) throws Exception {
        System.out.println(String.format("topic:  %s\ncontent:  %s", msg.getTopic(), msg.getContent()));
    }

    MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable throwable) {
            System.out.println("mqtt connection lost");
            throwable.printStackTrace();
            while (client != null && !client.isConnected()) {
                try {
                    open();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            RocketMsg msg = new RocketMsg(topic, Util.bytes2String(message.getPayload()));
            msg.setExt(message);
            onReceived(msg);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            onSendSuccess(token);
        }
    };

    public class DeliveryCompleteEvent {
        IMqttDeliveryToken token;

        public DeliveryCompleteEvent(IMqttDeliveryToken token) {
            this.token = token;
        }
    }

}
