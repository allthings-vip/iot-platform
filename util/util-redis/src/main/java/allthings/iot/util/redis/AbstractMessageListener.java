package allthings.iot.util.redis;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.Charset;

/**
 * @author :  sylar
 * @FileName :  AbstractMessageListener
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
public abstract class AbstractMessageListener implements MessageListener {
    private final static Charset CHARSET = Charsets.UTF_8;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理订阅到的消息
     *
     * @param topic
     * @param jsonMsg
     */
    protected abstract void handleMessage(String topic, String jsonMsg) throws Exception;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(message.getChannel(), CHARSET);
        String msg = new String(message.getBody(), CHARSET);

        try {
            handleMessage(topic, msg);
        } catch (Exception e) {
            LOG.warn("handleMessage error:{}", e.getMessage());
        }
    }
}
