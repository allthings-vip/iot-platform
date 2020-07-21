package allthings.iot.das.mqtt.bean;

import allthings.iot.das.mqtt.protocol.subscriptions.Subscription;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import io.netty.util.internal.ConcurrentSet;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :  sylar
 * @FileName :  MqttTopicCache
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class MqttTopicCache {
    private Cache<String, ConcurrentSet<String>> clientTopicCache = CacheBuilder.newBuilder().maximumSize(1000000L)
            .build();
    private Cache<String, Map<String, Subscription>> topicSubscriptionCache = CacheBuilder.newBuilder().maximumSize
            (1000000L).build();

    public Map<String, Subscription> getSubscriptions(String topic) {
        Map<String, Subscription> subscriptionMap = topicSubscriptionCache.getIfPresent(topic);
        if (subscriptionMap == null) {
            subscriptionMap = new ConcurrentHashMap<String, Subscription>(16);
        }
        return subscriptionMap;
    }

    public synchronized List<String> addSubscriptions(Collection<Subscription> subscriptions) {
        List<String> newTopicList = Lists.newArrayList();
        for (Subscription subscription : subscriptions) {
            if (addSubscription(subscription)) {
                newTopicList.add(subscription.getTopicFilter());
            }
        }
        return newTopicList;
    }

    private boolean addSubscription(Subscription subscription) {
        String clientId = subscription.getClientId();
        String topic = subscription.getTopicFilter();
        boolean isTopicNew = false;
        Map<String, Subscription> subscriptionMap = topicSubscriptionCache.getIfPresent(topic);
        if (subscriptionMap == null) {
            subscriptionMap = new ConcurrentHashMap<String, Subscription>(16);
            isTopicNew = true;
        }
        subscriptionMap.put(clientId, subscription);
        topicSubscriptionCache.put(topic, subscriptionMap);

        ConcurrentSet<String> topicSet = clientTopicCache.getIfPresent(clientId);
        if (topicSet == null) {
            topicSet = new ConcurrentSet<String>();
        }
        topicSet.add(topic);
        clientTopicCache.put(clientId, topicSet);
        return isTopicNew;
    }

    public synchronized List<String> removeSubscriptions(String clientId, Collection<String> topics) {
        List<String> needRemoveTopicList = Lists.newArrayList();
        for (String topic : topics) {
            if (removeSubscription(clientId, topic)) {
                needRemoveTopicList.add(topic);
            }
        }
        return needRemoveTopicList;
    }

    private boolean removeSubscription(String clientId, String topic) {
        boolean topicNeedRemove = false;
        Map<String, Subscription> subscriptions = topicSubscriptionCache.getIfPresent(topic);
        if (subscriptions != null) {
            subscriptions.remove(clientId);
            if (subscriptions.isEmpty()) {
                topicSubscriptionCache.invalidate(topic);
                topicNeedRemove = true;
            }
        }
        ConcurrentSet<String> topicSet = clientTopicCache.getIfPresent(clientId);
        if (topicSet != null) {
            topicSet.remove(topic);
            if (topicSet.isEmpty()) {
                clientTopicCache.invalidate(clientId);
            }
        }
        return topicNeedRemove;
    }

    public synchronized List<String> removeForClientId(String clientId) {
        List<String> needRemoveTopicList = Lists.newArrayList();
        ConcurrentSet<String> topicSet = clientTopicCache.getIfPresent(clientId);
        if (topicSet != null) {
            for (String topic : topicSet) {
                Map<String, Subscription> subscriptions = topicSubscriptionCache.getIfPresent(topic);
                if (subscriptions != null) {
                    subscriptions.remove(clientId);
                    if (subscriptions.isEmpty()) {
                        topicSubscriptionCache.invalidate(topic);
                        needRemoveTopicList.add(topic);
                    }
                }
            }
            clientTopicCache.invalidate(clientId);
        }
        return needRemoveTopicList;
    }

    public long getClientSize() {
        return clientTopicCache.size();
    }

    public long getTopicSize() {
        return topicSubscriptionCache.size();
    }

    public long getSubscriptionSize() {
        long size = 0;
        for (Map<String, Subscription> subscriptions : topicSubscriptionCache.asMap().values()) {
            size += subscriptions.size();
        }
        return size;
    }

}
