package allthings.iot.util.redis.impl;

import com.alibaba.fastjson.JSON;
import allthings.iot.util.redis.IMessageQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author :  sylar
 * @FileName :  MessageQueueServiceImpl
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
@Component
public class MessageQueueServiceImpl implements IMessageQueueService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageQueueServiceImpl.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Override
    public long getRemainSize(String queueName) {
        return stringRedisTemplate.boundListOps(queueName).size();
    }


    @Override
    public boolean containsQueue(String queueName) {
        return stringRedisTemplate.hasKey(queueName);
    }

    @Override
    public <Msg> void sendMessage(String queueName, Msg msg) {
        try {
            String json = JSON.toJSONString(msg);
            stringRedisTemplate.boundListOps(queueName).rightPush(json);

            LOG.info("sendMessage to queue:{}\nmessage:{}", queueName, json);
        } catch (Exception e) {
            LOG.warn("sendMessage error. \nqueue:{}\nobject:{}\nexception:{}", queueName, msg, e.getMessage());
        }
    }

    @Override
    public <Msg> Msg receiveMessage(String queueName, Class<Msg> clazz) {
        try {
            String json = stringRedisTemplate.boundListOps(queueName).leftPop();
            if (json != null) {
                LOG.info("receiveMessage from queue:{}\nmessage:{}", queueName, json);
                return JSON.parseObject(json, clazz);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.warn("receiveMessage error. \nqueue:{}\nexception:{}", queueName, e.getMessage());
            return null;
        }
    }
}
