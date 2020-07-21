package allthings.iot.das.common.bean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * @author :  sylar
 * @FileName :  TerminalCache
 * @CreateDate :  2017/11/08
 * @Description :  key 为 终端设备id
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
public class TerminalCache {

    private Cache<String, InetSocketAddress> channelCache = CacheBuilder.newBuilder()
            .maximumSize(1000000L).concurrencyLevel(16).build();

    public InetSocketAddress get(String key) {
        return channelCache.getIfPresent(key);
    }

    public void put(String key, InetSocketAddress address) {
        if (address != channelCache.getIfPresent(key)) {
            channelCache.put(key, address);
        }
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
}
