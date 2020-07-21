package allthings.iot.util.rocketmq.ons.mqtt;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.IConsumerConfig;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttConsumer extends AbsClient implements IConsumer {

    /**
     * 设置订阅方订阅的Topic集合，此处遵循MQTT的订阅规则，可以是一级Topic，二级Topic，P2P消息请订阅/p2p
     * final String[] topicArray = new String[]{topic + "/notice/", topic + "/p2p"};
     * final int[] qos = {0, 0};
     **/
    String[] topicArray;
    int[] qos;
    IRocketMsgListener listener;
    IConsumerConfig config;

    public OnsMqttConsumer(OnsMqttFactory factory, IConsumerConfig config) {
        super(factory);
        this.config = config;

        init();
    }

    void init() {
    }


    @Override
    public void subscribe(String topic, String[] tags, IRocketMsgListener listener) {
        Preconditions.checkNotNull(topic, "topic is null");
        Preconditions.checkNotNull(listener, "listener is null");

        //合成 topicArray
        List<String> topics = Lists.newArrayList();
        if (tags == null || tags.length == 0) {
            topics.add(topic);
        } else {
            for (String filter : tags) {
                if (!filter.startsWith("/")) {
                    filter = "/" + filter;
                }

                topics.add(topic + filter);
            }
        }
        this.topicArray = new String[topics.size()];
        topics.toArray(this.topicArray);
        this.listener = listener;
        this.qos = new int[this.topicArray.length];

        //
        unsubscribe();

        try {
            open();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe() {
        try {
            if (topicArray != null && client.isConnected()) {
                client.unsubscribe(topicArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void open() throws MqttException {
        super.open();
        if (topicArray != null) {
            System.out.println("订阅：" + Lists.newArrayList(topicArray));
            client.subscribe(topicArray, qos);
        }
    }

    @Override
    protected void onReceived(RocketMsg msg) throws Exception {
        super.onReceived(msg);
        if (listener != null) {
            listener.onSuccess(Lists.newArrayList(msg));
        }
    }
}
