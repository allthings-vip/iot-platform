package allthings.iot.util.jedis;

import com.google.common.base.Preconditions;
import com.sohu.tv.builder.ClientBuilder;
import com.sohu.tv.cachecloud.client.basic.enums.RedisTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author :  luhao
 * @FileName :  CacheCloudRedisFactory
 * @CreateDate :  2018-5-22
 * @Description : redis工厂
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class CacheCloudRedisFactory {

    protected static final Logger log = LoggerFactory.getLogger(CacheCloudRedisFactory.class);

    /**
     * 默认资源文件路径
     */
    public static final String DEFAULT_PROP_FILEPATH = "config/CacheCloud.properties";

    /**
     * 默认失效时间，单位：秒
     */
    private static final int DEFAULT_EXPIRE_SECOND = 60;

    /**
     * Jedis sentinel 连接池
     */
    JedisSentinelPool jedisSentinelPool = null;


    /**
     * 默认构造函数，会自动从默认配置文件 ["config/CacheCloud.properties"] 读取必要参数
     */
    public CacheCloudRedisFactory() throws Exception {
        this.initWithProp(null, null);
    }

    /**
     * 指定资源文件路径的构造函数。具体参数的key沿用默认的。
     *
     * @param propFilepath 资源文件路径
     * @throws Exception 异常时，无法构造实例
     */
    public CacheCloudRedisFactory(String propFilepath) throws Exception {
        this.initWithProp(propFilepath, null);
    }

    /**
     * 使用默认的资源文件路径
     *
     * @param configProp 资源文件配置
     * @throws Exception 异常时，无法构造实例
     */
    public CacheCloudRedisFactory(CommonConfigProp configProp) throws Exception {
        this.initWithProp(null, configProp);
    }

    /**
     * 指定资源文件路径、资源文件配置信息
     *
     * @param propFilepath 资源文件路径
     * @param configProp   资源文件配置
     * @throws Exception 异常时，无法构造实例
     */
    public CacheCloudRedisFactory(String propFilepath, CommonConfigProp configProp) throws Exception {
        this.initWithProp(propFilepath, configProp);
    }

    /**
     * 个性化构造函数
     *
     * @param redisTypeEnum   Redis类型，非null
     * @param cacheCloudAppId CacheCloud应用中的appId，非null，大于0的long型数据
     */
    public CacheCloudRedisFactory(RedisTypeEnum redisTypeEnum, Long cacheCloudAppId, String appKey) throws Exception {
        this.init(redisTypeEnum, cacheCloudAppId, appKey, null);
    }


    private void initWithProp(String propFilepath, CommonConfigProp configProp) throws Exception {
        if (propFilepath == null || propFilepath.trim().length() == 0) {
            propFilepath = DEFAULT_PROP_FILEPATH;
        }

        if (configProp == null) {
            configProp = new CommonConfigProp();
        }

        String redisTypeKey = configProp.concatPrefixAndSuffix(configProp.getRedisTypeKey());
        String redisType = PropUtil.getPropValue(propFilepath, redisTypeKey, null);
        String appIdKey = configProp.concatPrefixAndSuffix(configProp.getAppIdKey());
        String appIdStr = PropUtil.getPropValue(propFilepath, appIdKey, null);
        String appKeyKey = configProp.concatPrefixAndSuffix(configProp.getAppKeyKey());
        String appkeyStr = PropUtil.getPropValue(propFilepath, appKeyKey, null);


        RedisTypeEnum redisTypeEnum = null;
        long appId = 0;
        try {
            if (redisType == null || redisType.trim().length() == 0) {
                redisTypeEnum = RedisTypeEnum.SENTINEL;
            } else {
                redisTypeEnum = RedisTypeEnum.valueOf(redisType);
            }

            appId = Long.parseLong(appIdStr);
        } catch (Exception e) {
            String msg = propFilepath + "{" + redisTypeKey + ":" + redisType + ", " + appIdKey + ":" + appIdStr + "}." +
                    " \n";
            log.warn(msg + e.getMessage());
            throw new Exception(msg);
        }

        if (StringUtils.isBlank(appkeyStr)) {
            String msg = propFilepath + "{" + appKeyKey + ":" + appkeyStr + "}. \n Invalid param appKey !";
            log.warn(msg);
            throw new Exception(msg);
        }


        /* 连接池配置 */
        GenericObjectPoolConfig poolConfig = null;
        try {
            String minIdleKey = configProp.concatPrefixAndSuffix(configProp.getPoolMinIdleKey());
            String minIdleStr = PropUtil.getPropValue(propFilepath, minIdleKey, null);
            String maxIdleKey = configProp.concatPrefixAndSuffix(configProp.getPoolMaxIdleKey());
            String maxIdleStr = PropUtil.getPropValue(propFilepath, maxIdleKey, null);
            String maxTotalKey = configProp.concatPrefixAndSuffix(configProp.getPoolMaxTotalKey());
            String maxTotalStr = PropUtil.getPropValue(propFilepath, maxTotalKey, null);

            int minIdle = 0;
            int maxIdle = 0;
            int maxTotal = 0;
            if (minIdleStr != null) {
                minIdle = Integer.parseInt(minIdleStr);
            }
            if (maxIdleStr != null) {
                maxIdle = Integer.parseInt(maxIdleStr);
            }
            if (maxTotalStr != null) {
                maxTotal = Integer.parseInt(maxTotalStr);
            }

            poolConfig = new GenericObjectPoolConfig();
            if (minIdle > 0) {
                poolConfig.setMinIdle(minIdle);
            }
            if (maxIdle > 0 && maxIdle > minIdle) {
                poolConfig.setMaxIdle(maxIdle);
            }
            if (maxTotal > 0) {
                poolConfig.setMaxTotal(maxTotal);
            }
        } catch (Exception e) {
            String msg = "Read pool config ERROR!";
            // 连接池配置信息读取异常时，将只记录日志警告，并使用默认配置。而不抛异常。
            log.warn(msg, e);
        }

        this.init(redisTypeEnum, appId, appkeyStr, poolConfig);
    }


    /**
     * 初始化
     *
     * @param redisTypeEnum   Redis类型，非null
     * @param cacheCloudAppId CacheCloud应用中的appId，非null，大于0的long型数据
     */
    private void init(RedisTypeEnum redisTypeEnum, Long cacheCloudAppId, String appKey, GenericObjectPoolConfig
            poolConfig) throws Exception {
        if (redisTypeEnum == null || (cacheCloudAppId == null || cacheCloudAppId <= 0)) {
            String msg = "Invalid input params:{redisTypeEnum:" + redisTypeEnum + ", cacheCloudAppId:" +
                    cacheCloudAppId;
            log.warn(msg);
            throw new Exception(msg);
        }

        // 默认连接池配置
        if (poolConfig == null) {
            poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(100);
            poolConfig.setMaxIdle(1000);
            poolConfig.setMaxTotal(10000);
        }

        switch (redisTypeEnum) {
            case SENTINEL:
            default:
                jedisSentinelPool = this.buildJedisSentinelPool(cacheCloudAppId, appKey, poolConfig);
        }
    }


    /**
     * 构建 jedis 的 sentinel 连接池
     *
     * @param cacheCloudAppId CacheCloud 平台中的应用ID
     * @param poolConfig      jedis连接池设置
     * @return 连接池
     */
    private JedisSentinelPool buildJedisSentinelPool(Long cacheCloudAppId, String appkey, GenericObjectPoolConfig
            poolConfig) {
        if (cacheCloudAppId == null || cacheCloudAppId <= 0) {
            return null;
        }

        if (poolConfig == null) {
            poolConfig = new GenericObjectPoolConfig();
        }

        return ClientBuilder.redisSentinel(cacheCloudAppId)
                .setConnectionTimeout(1000)
                .setSoTimeout(5000)
                .setPoolConfig(poolConfig)
                .buildWithSafeCheck(true, appkey);
    }



    /* ****************************** */

    /**
     * 检查jedis连接池是否初始化
     */
    private void checkJedisPool() throws RuntimeException {
        if (jedisSentinelPool == null) {
            String msg = "The jedisSentinelPool is null !";
            log.warn(msg);
            throw new RuntimeException(msg);
        }
    }


    /**
     * 测试服务器是否正常
     *
     * @return 正常情况下，返回：PONG
     */
    public String ping() {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.ping();
        } catch (Exception e) {
            log.error("Redis get ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 检查 key 是否存在
     *
     * @param key 键
     * @return true:存在; false:不存在; null: key 为null，或系统出现异常情况，请关注warn日志！
     */
    public Boolean exists(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            log.error("Redis get ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 对 key 增加 1
     *
     * @param key 键
     * @return 执行结果
     */
    public Long incr(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            log.error("Redis incr ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 对 key 增加 number。（如果number为负数，则为减少）
     *
     * @param key    键
     * @param number 长整形增量
     * @return 执行结果
     */
    public Long incrByLong(final String key, final Long number) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return -1L;
        }

        if (key == null || number == null) {
            log.warn("Key or number is null !");
            return -1L;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.incrBy(key, number);
        } catch (Exception e) {
            log.error("Redis incrBy ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return -1L;
    }

    /**
     * 对 key 增加 number。（如果number为负数，则为减少）
     *
     * @param key    键
     * @param number 浮点型增量
     * @return 执行结果
     */
    public Double incrByDouble(final String key, final Double number) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null || number == null) {
            log.warn("Key or number is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.incrByFloat(key, number);
        } catch (Exception e) {
            log.error("Redis incrByFloat ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * <b>分布式锁<b/><br/>
     *
     * <p>
     * Trying:<br/>
     * <b>因redis实现的分布式锁，受影响的因素很多，（如，服务器时间跳跃，失效转移，GC阻塞......），并非绝对安全！！！</b><br/>
     * <b>相比于zookeeper的实现方式，我更推荐使用后者---zk的实现方式。</b><br/>
     * <b>如果你的应用要求严格的分布式锁，更建议使用zk实现的分布式锁！！！！</b><br/>
     * </p>
     *
     * @param key         锁名称，建议使用 warenam.lockname，即： war包.锁名
     * @param liveSeconds 锁的存活时间，单位：秒
     * @return 是否加锁成功
     * @author Trying
     */
    @Deprecated
    public Boolean lock(final String key, final int liveSeconds) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return false;
        }

        if (key == null) {
            log.warn("Key is null !");
            return false;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            Long rst = jedis.setnx(key, String.valueOf(System.currentTimeMillis() + liveSeconds * 1000 + 1));
            if (rst != null) {
                if (rst == 1) {
                    return true;
                } else {  // 检查是否锁超时，如程序异常，加锁后故障一直未释放锁。超时的锁，可以释放，供重新使用！
                    String oldDeadlineValue = jedis.get(key);
                    long oldDeadline = parseLong(oldDeadlineValue);
                    if (oldDeadline == 0L) {
                        log.warn("The lock data value is [" + key + "," + oldDeadlineValue + "].");
                    }

                    // 超时？当前线程尝试加锁
                    if (oldDeadline < System.currentTimeMillis()) {
                        String oldDeadlineValue2 = jedis.getSet(key, String.valueOf(System.currentTimeMillis() +
                                liveSeconds * 1000 + 1));
                        if (oldDeadline == parseLong(oldDeadlineValue2)) {
                            return true;
                        }
                    }
                }
            } else {
                log.warn("Redis setnx return null !");
            }
        } catch (Exception e) {
            log.error("Redis lock ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }

    private long parseLong(String data) {
        long rst = 0;
        try {
            rst = Long.parseLong(data);
        } catch (Exception e) {
            log.error("Trying ERROR!", e);
        }

        return rst;
    }

    /**
     * <b>解锁</b><br/>
     * 将删除该锁对应的key。
     *
     * <p>
     * Trying:<br/>
     * <b>因redis实现的分布式锁，受影响的因素很多，（如，服务器时间跳跃，失效转移，GC阻塞......），并非绝对安全！！！</b><br/>
     * <b>相比于zookeeper的实现方式，我更推荐使用后者---zk的实现方式。</b><br/>
     * <b>如果你的应用要求严格的分布式锁，更请使用zk实现的分布式锁！！！！</b><br/>
     * </p>
     * <p>
     * [author: Trying @ 20170608]
     *
     * @param key 锁码
     * @return 解锁是否成功
     */
    @Deprecated
    public boolean unlock(final String key) {
        if (jedisSentinelPool == null) {
            String msg = "jedisSentinelPool is null !";
            log.error(msg);
            return false;
        }

        if (key == null) {
            String msg = "Key is null !";
            log.warn(msg);
            return false;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();

            long current1 = System.currentTimeMillis();
            String oldDeadlineValue = jedis.get(key);
            long oldDeadline = parseLong(oldDeadlineValue);

            // 还未超时,并且当前线程并为阻塞超过 50 毫秒 ，则解锁
            if (oldDeadline > System.currentTimeMillis() && System.currentTimeMillis() - current1 < 50) {
                Long rst = jedis.del(key);

                return rst == 1;
            }

        } catch (Exception e) {
            String msg = "Redis unlock ERROR!";
            log.error(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }


    /**
     * 设置字符串缓存，使用默认存活时长60秒
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        this.set(key, value, DEFAULT_EXPIRE_SECOND);
    }

    /**
     * 设置字符串缓存
     *
     * @param key      键
     * @param value    值
     * @param liveTime 存活时长，单位：秒。如果持久化保存，就设置为-1
     */
    public void set(String key, String value, int liveTime) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return;
        }

        if (key == null || value == null) {
            log.warn("Key or Value is null :key=" + key + "---------value=" + value);
            return;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.set(key, value);
            if (liveTime > 0) {
                jedis.expire(key, liveTime);
            }
        } catch (Exception e) {
            log.error("Redis set ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 如果不存在，则设置字符串缓存
     *
     * @param key      键
     * @param value    值
     * @param liveTime 存活时长，单位：秒。如果持久化保存，就设置为-1
     */
    public boolean setnx(String key, String value, int liveTime) throws Exception {
        if (jedisSentinelPool == null) {
            String msg = "The jedisSentinelPool is null !";
            log.warn(msg);
            throw new RuntimeException(msg);
        }

        if (key == null || value == null) {
            String msg = "Key or Value is null :key=" + key + "---------value=" + value;
            log.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            Long executeRST = jedis.setnx(key, value);
            boolean rst = false;
            if (executeRST != null && executeRST == 1L) {
                rst = true;
            }
            if (rst && liveTime > 0) {
                jedis.expire(key, liveTime);
            }
            return rst;
        } catch (Exception e) {
            log.error("Redis setnx ERROR!", e);
            throw new Exception(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 如果不存在，则设置字符串缓存，使用默认存活时长60秒
     *
     * @param key   键
     * @param value 值
     */
    public boolean setnx(String key, String value) throws Exception {
        return this.setnx(key, value, DEFAULT_EXPIRE_SECOND);
    }

    /**
     * 设置指定key的存活时长。<br/>
     * 一般用于再次延长key的存活时长。
     *
     * @param key      键
     * @param liveTime 存活时长，单位：秒。如果持久化保存，就设置为-1
     */
    public void expire(String key, int liveTime) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return;
        }

        if (key == null) {
            log.warn("Key is null !");
            return;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.expire(key, liveTime);
        } catch (Exception e) {
            log.error("Redis set ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 计算key的剩余存活时长，秒为单位
     *
     * @param key 键
     */
    public Long ttl(String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("Redis ttl ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 计算key的剩余存活时长，秒为单位
     *
     * @param key 键
     */
    public Long ttl(byte[] key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("Redis ttl ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 计算key的剩余存活时长，毫秒为单位
     *
     * @param key 键
     */
    public Long pttl(String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.pttl(key);
        } catch (Exception e) {
            log.error("Redis set ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 读取字符串缓存
     *
     * @param key 键
     * @return 缓存的字符串值
     */
    public String get(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            log.error("Redis get ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * getset
     *
     * @param key 键
     * @return 缓存的字符串值
     */
    public String getSet(final String key, final String value) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("Redis getset ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 批量读取字符串缓存
     *
     * @param keys 键
     * @return 缓存的字符串值
     */
    public List<String> mget(final String... keys) throws RuntimeException {
        if (jedisSentinelPool == null) {
            String msg = "The jedisSentinelPool is null !";
            log.warn(msg);
            throw new RuntimeException(msg);
        }

        if (keys == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.mget(keys);
        } catch (Exception e) {
            String msg = "Redis get ERROR!";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 删除指定的缓存key
     *
     * @param keys 待删除的key
     * @return 执行结果
     */
    public long del(final String... keys) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return 0;
        }

        if (keys == null || keys.length == 0) {
            log.warn("Keys is null !");
            return 0;
        }

        long rst = 0;
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            rst = jedis.del(keys);
        } catch (Exception e) {
            log.error("Redis get ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return rst;
    }


    /**
     * hash 类型的 redis 数据缓存。默认存活60秒。
     *
     * @param key   redis 的缓存 key
     * @param field 缓存的 hash 数据的字段名
     * @param value 缓存的 hash 数据的字段值
     */
    public void hset(String key, String field, String value) {
        this.hset(key, field, value, DEFAULT_EXPIRE_SECOND);
    }

    /**
     * hash 类型的 redis 数据缓存
     *
     * @param key      redis 的缓存 key
     * @param field    缓存的 hash 数据的字段名
     * @param value    缓存的 hash 数据的字段值
     * @param liveTime 存活时长，秒
     */
    public void hset(String key, String field, String value, int liveTime) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return;
        }

        if (key == null || field == null || value == null) {
            log.warn("Input param is null :key=" + key + ", field=" + field + ", value=" + value);
            return;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hset(key, field, value);
            if (liveTime > 0) {
                jedis.expire(key, liveTime);
            }
        } catch (Exception e) {
            log.error("Redis hset ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 读取hash缓存
     *
     * @param key 键
     * @return hash数据属性值
     */
    public String hget(final String key, final String field) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            log.error("Redis hget ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * hash 类型的 redis 数据缓存。同时缓存多个属性。批量缓存。
     * 默认存活60秒。
     *
     * @param key  redis 的缓存 key
     * @param hash 缓存的 hash 数据。字段名：字段值
     */
    public void hmset(String key, Map<String, String> hash) {
        this.hmset(key, hash, DEFAULT_EXPIRE_SECOND);
    }

    /**
     * hash 类型的 redis 数据缓存。同时缓存多个属性。批量缓存。
     *
     * @param key      redis 的缓存 key
     * @param hash     缓存的 hash 数据。字段名：字段值
     * @param liveTime 存活时长，秒
     */
    public void hmset(String key, Map<String, String> hash, int liveTime) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return;
        }

        if (key == null || hash == null || hash.size() == 0) {
            log.warn("Input param is null :key=" + key + ", hash=" + hash);
            return;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hmset(key, hash);
            if (liveTime > 0) {
                jedis.expire(key, liveTime);
            }
        } catch (Exception e) {
            log.error("Redis hmset ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 批量读取hash缓存指定的属性值
     *
     * @param key   键
     * @param field hash数据的属性数组。
     * @return hash数据的属性值列表。
     */
    public List<String> hmget(final String key, final String... field) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null || field == null) {
            log.warn("Key or field is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hmget(key, field);
        } catch (Exception e) {
            log.error("Redis hmget ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * 批量读取hash缓存指定的属性值
     *
     * @param key   键
     * @param field hash数据的属性数组。
     * @return hash数据的属性值列表。
     */
    public Long hincrBy(final String key, final String field, final long increment) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null || field == null) {
            log.warn("Key or field is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            log.error("Redis hincrBy ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 批量读取hash缓存
     *
     * @param key 键
     * @return hash数据的值。
     */
    public Map<String, String> hgetAll(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("Redis hgetAll ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * 批量读取hash缓存
     *
     * @param key 键
     * @return hash数据的值。
     */
    public void hdel(String key, String... field) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return;
        }

        if (key == null || field == null) {
            log.warn("Key or field is null !");
            return;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            log.error("Redis hdel ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * hash 扫描 。
     * 注意：原 scanHashs 方法的调用，返回的类型是spring-data-bean包中的，这里需要切换！！！
     *
     * @param key     指定 hash 的 key
     * @param pattern 正则
     * @param count   统计量
     * @return 扫描结果
     */
    public ScanResult<Map.Entry<String, String>> hscan(String key, String pattern, Integer count) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        ScanResult<Map.Entry<String, String>> scanResult = null;
        Jedis jedis = null;

        try {
            jedis = jedisSentinelPool.getResource();

            ScanParams scanParams = null;
            if (pattern != null || count != null) {
                scanParams = new ScanParams();
            }
            if (count != null) {
                scanParams.count(count);
            }
            if (pattern != null) {
                scanParams.match(pattern);
            }

            if (scanParams != null) {
                scanResult = jedis.hscan(key, ScanParams.SCAN_POINTER_START, scanParams);
            } else {
                scanResult = jedis.hscan(key, ScanParams.SCAN_POINTER_START);
            }
        } catch (Exception e) {
            log.error("Redis hscan ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return scanResult;
    }


    /**
     * 集合添加元素
     *
     * @param key     集合的key
     * @param members 待添加的集合中的元素
     * @return 执行结果
     */
    public Long sadd(final String key, final String... members) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null || members == null || members.length == 0) {
            log.warn("Key or members is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.sadd(key, members);
        } catch (Exception e) {
            log.error("Redis sadd ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 统计集合中元素数
     *
     * @param key 集合的key
     * @return 执行结果
     */
    public Long scard(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("Redis scard ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * 查询集合中的元素
     *
     * @param key 集合的key
     * @return 集合元素
     */
    public Set<String> smembers(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            log.error("Redis smembers ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * Set （集合）类型，删除集合中的元素。
     * 如果指定的key存在，返回null。如果key不是一个集合，返回错误。
     * 如果合法，将返回删除的元素数，被忽略的除外。
     *
     * @param key     Set类型的键
     * @param members 元素数组
     * @return 删除元素数。
     */
    public Long srem(final String key, final String... members) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.srem(key, members);
        } catch (Exception e) {
            log.error("Redis srem ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 从队列左侧推入
     *
     * @param key    队列的key
     * @param values 待推入的队列元素值
     * @return 执行结果
     */
    public Long lpush(final String key, final String... values) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null || values == null) {
            log.warn("Key or values is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.lpush(key, values);
        } catch (Exception e) {
            log.error("Redis lpush ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 从队列右侧推入
     *
     * @param key    队列的key
     * @param values 待推入的队列元素值
     * @return 执行结果
     */
    public Long rpush(final String key, final String... values) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null || values == null) {
            log.warn("Key or values is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.rpush(key, values);
        } catch (Exception e) {
            log.error("Redis rpush ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 从队列左侧弹出
     *
     * @param key 队列的key
     * @return 弹出的元素
     */
    public String lpop(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            log.error("Redis lpop ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 从队列右侧弹出
     *
     * @param key 队列的key
     * @return 弹出的元素
     */
    public String rpop(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.rpop(key);
        } catch (Exception e) {
            log.error("Redis rpop ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 获取list的长度，元素数
     *
     * @param key 队列的key
     * @return 弹出的元素
     */
    public Long llen(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            log.error("Redis llen ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * 取指定范围的列表元素
     *
     * @param key 队列的key
     * @return 元素
     */
    public List<String> lrange(final String key, final long start, final long end) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("Redis lrange ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param key
     * @param score
     * @param member
     * @return 操作状态，1 or 0
     */
    public Long zadd(final String key, final double score, String member) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zadd(key, score, member);
        } catch (Exception e) {
            log.error("Redis zaad ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param key
     * @param scoreMembers
     * @return 操作状态，1 or 0
     */
    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            log.error("Redis zaad ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 获取有序集合的成员数
     *
     * @param key
     * @return 集合成员数量
     */
    public Long zcard(final String key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            log.error("Redis zcard ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 移除有序集合中给定的分数区间的所有成员
     *
     * @param key
     * @param start
     * @param end
     * @return 移除元素的数量
     */
    public Long zremrangeByScore(final String key, final double start, final double end) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            log.error("Redis zremrangeByScore ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 通过分数返回有序集合指定区间内的成员
     *
     * @param key
     * @param min
     * @param max
     * @return 元素集合
     */
    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("Redis zrangeByScore ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }


    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
     *
     * @param key 有序集合的key
     * @param min score范围的最小值
     * @param max score范围的最大值
     * @return 元素集合
     */
    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) throws Exception {
        checkJedisPool();

        Preconditions.checkArgument(StringUtils.isNotBlank(key), "The param[key] is blank !");
        Preconditions.checkArgument(max >= min, "The param[max] is less than param[min] !");

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            String msg = "Redis zrangeByScore ERROR!";
            log.error(msg, e);
            throw new Exception(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 通过索引区间返回有序集合成指定区间内的成员
     *
     * @param key
     * @param start
     * @param end
     * @return 元素集合
     */
    public Set<String> zrange(final String key, final long start, final long end) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            log.error("Redis zrange ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     *
     * @param key
     * @param member
     * @return 排名
     */
    public Long zrank(final String key, final String member) throws Exception {
        if (jedisSentinelPool == null) {
            String msg = "The jedisSentinelPool is null !";
            log.warn(msg);
            throw new RuntimeException(msg);
        }

        if (StringUtils.isBlank(key) || StringUtils.isBlank(member)) {
            String msg = "Key or member is blank !";
            log.warn(msg);
            throw new RuntimeException(msg);
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zrank(key, member);
        } catch (Exception e) {
            String msg = "Redis zrank ERROR!";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 计算在有序集合中指定区间分数的成员数
     *
     * @param key
     * @param min
     * @param max
     * @return 区间集合成员数量
     */
    public Long zcount(final String key, final double min, final double max) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zcount(key, min, max);
        } catch (Exception e) {
            log.error("Redis zcount ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key
     * @param member
     * @return 移除状态，0表示已移除，1表示集合不存在要移除的元素
     */
    public Long zrem(String key, String... member) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zrem(key, member);
        } catch (Exception e) {
            log.error("Redis zrem ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 有序集合中对指定成员的分数加上增量 increment
     *
     * @param key
     * @param score
     * @param member
     * @return 新的score值
     */
    public Double zincrby(String key, double score, String member) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            log.error("Redis zincrby ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 返回有序集中，成员的分数值
     *
     * @param key
     * @param member
     * @return 成员的score值
     */
    public Double zscore(final String key, final String member) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            log.error("Redis zscore ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 字节格式的get方法
     *
     * @param key
     * @return 以字节格式返回
     */
    public byte[] get(final byte[] key) {
        if (jedisSentinelPool == null) {
            log.warn("jedisSentinelPool is null !");
            return null;
        }

        if (key == null) {
            log.warn("Key is null !");
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            log.error("Redis get ERROR!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    public JedisSentinelPool getJedisSentinelPool() {
        return jedisSentinelPool;
    }
}
