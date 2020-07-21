package allthings.iot.util.redis.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import allthings.iot.util.redis.AbstractMessageListener;
import allthings.iot.util.redis.ISubscribePublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author :  sylar
 * @FileName :  SubscribePublishServiceImpl
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
@Component
public class SubscribePublishServiceImpl implements ISubscribePublishService {
    private static final Logger LOG = LoggerFactory.getLogger(SubscribePublishServiceImpl.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisMessageListenerContainer redisMessageListenerContainer;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory jedisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);

        //namethreadfactory
        ThreadFactory subNameThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("subscription-pool-%d").build();
        Executor subscriptionExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), subNameThreadFactory);

        //namethreadfactory
        ThreadFactory taskNameThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-pool-%d").build();
        Executor taskExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), taskNameThreadFactory);

        container.setSubscriptionExecutor(subscriptionExecutor);
        container.setTaskExecutor(taskExecutor);
        return container;
    }

    @Override
    public void publishMessage(String topic, Object msg) {
        Preconditions.checkNotNull(topic, "topic is null");
        Preconditions.checkNotNull(msg, "msg is null");

        String objString = JSON.toJSONString(msg);
        stringRedisTemplate.convertAndSend(topic, objString);

        LOG.info("publishMessage by topic:{}\nmessage:{}", topic, objString);
    }

    @Override
    public void subscribeMessage(AbstractMessageListener messageListener, List<String> topics) {
        Preconditions.checkNotNull(messageListener, "messageListener is null");
        Preconditions.checkNotNull(topics, "topics is null");
        Preconditions.checkState(topics.size() > 0, "invalid topics");

        //先取消订阅
        unsubscribeMessage(messageListener, null);

        //重新订阅
        List<Topic> channelTopics = convertPatternTopics(topics);
        redisMessageListenerContainer.addMessageListener(messageListener, channelTopics);
    }

    @Override
    public void unsubscribeMessage(AbstractMessageListener messageListener, List<String> topics) {
        Preconditions.checkNotNull(messageListener, "messageListener is null");

        List<Topic> channelTopics = convertPatternTopics(topics);
        redisMessageListenerContainer.removeMessageListener(messageListener, channelTopics);
    }


    private List<Topic> convertTopics(List<String> topics) {
        List<Topic> channelTopics = Lists.newArrayList();
        for (String topic : topics) {
            if (!Strings.isNullOrEmpty(topic)) {
                channelTopics.add(new ChannelTopic(topic));
            }
        }

        return channelTopics;
    }


    private List<Topic> convertPatternTopics(List<String> topics) {
        List<Topic> res = Lists.newArrayList();
        for (String topic : topics) {
            if (!Strings.isNullOrEmpty(topic)) {
                res.add(new PatternTopic(topic));
            }
        }

        return res;
    }
}
