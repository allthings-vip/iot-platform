package allthings.iot.das.common.bean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;


/**
 * @author :  sylar
 * @FileName :  ChannelCache
 * @CreateDate :  2017/11/08
 * @Description :  key : clientId   clientId = deviceType + deviceId
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
public class ChannelCache {

    private Cache<String, Channel> channelCache = CacheBuilder.newBuilder()
            .maximumSize(1000000L).concurrencyLevel(16).build();

    public Channel get(String key) {
        return channelCache.getIfPresent(key);
    }

    public void put(String key, Channel channel) {
        if (channel != channelCache.getIfPresent(key)) {
            channelCache.put(key, channel);
        }
    }

    /**
     * 根据通道channel拿到对应的设备信息
     *
     * @param channel
     * @return
     */
    public String getKey(Channel channel) {
        ConcurrentMap<String, Channel> channelMap = channelCache.asMap();
        Set<Map.Entry<String, Channel>> channelSet = channelMap.entrySet();
        for (Map.Entry<String, Channel> entry : channelSet) {
            if (entry.getValue().equals(channel)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public void remove(String key) {
        channelCache.invalidate(key);
    }

    public boolean containsKey(String key) {
        return channelCache.getIfPresent(key) != null;
    }

    public long size() {
        return channelCache.size();
    }

    public Cache<String, Channel> getChannelCache() {
        return channelCache;
    }

//    private class ChannelRemovalListener implements RemovalListener<String, Channel> {
//        @Override
//        public void onRemoval(RemovalNotification<String, Channel> removal) {
//            Channel channel = removal.getValue();
//            if (channel != null && channel.isActive()) {
//                channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
//            }
//        }
//    }
}
