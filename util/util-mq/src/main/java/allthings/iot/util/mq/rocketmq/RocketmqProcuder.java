package allthings.iot.util.mq.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.google.common.base.Charsets;
import allthings.iot.util.mq.AbstractProcuder;
import allthings.iot.util.mq.Message;


/**
 * @author :  sylar
 * @FileName :  MqttConst
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class RocketmqProcuder extends AbstractProcuder {

    protected DefaultMQProducer producer;

    @Override
    public void start() throws Exception {
        super.start();

        producer = new DefaultMQProducer();
        producer.setNamesrvAddr(brokers);
        producer.setProducerGroup(groupId);
        producer.setInstanceName(clientId);

        //producer.setVipChannelEnabled(false);
        producer.start();
    }

    @Override
    public void send(Message message) throws Exception {
        super.send(message);
        producer.send(new com.alibaba.rocketmq.common.message.Message(message.getTopic(), message.getContent()
                .getBytes(Charsets.UTF_8)));
    }

    @Override
    public void stop() {

        super.stop();

        if (producer != null) {
            producer.shutdown();
        }
    }

}
