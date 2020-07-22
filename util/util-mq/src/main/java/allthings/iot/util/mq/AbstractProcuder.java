package allthings.iot.util.mq;

import com.google.common.base.Preconditions;

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
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractProcuder extends AbstractClient implements IProducer {

    @Override
    public void start() throws Exception {
        stop();
    }

    @Override
    public void send(Message message) throws Exception {
        Preconditions.checkNotNull(message, "message is null");
        Preconditions.checkNotNull(message.getTopic(), "message topic is null");
        Preconditions.checkNotNull(message.getContent(), "message content is null");
    }

    @Override
    public void stop() {

    }
}
