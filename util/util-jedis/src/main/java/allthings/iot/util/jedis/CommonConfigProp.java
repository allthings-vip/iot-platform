package allthings.iot.util.jedis;


/**
 * @author :  luhao
 * @FileName :  CommonConfigProp
 * @CreateDate :  2018-5-22
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
public class CommonConfigProp {

    /** 前缀 */
    private String prefix = "CacheCloud.";

    /** 后缀 */
    private String suffix = null;

    /**
     * 资源文件中，redis 类型的key。
     * 其value对应 RedisTypeEnum @{@link com.sohu.tv.cachecloud.client.basic.enums.RedisTypeEnum}
     * 默认为:"SENTINEL"。
     */
    private String redisTypeKey = "redisType";

    /**
     * 资源文件中，CacheCloud 应用中的 appId 的 key
     * 其value 为 long 型的正数。
     */
    private String appIdKey = "appId";

    /**
     * 资源文件中，CacheCloud 应用中的 appKey 的 key
     * 其value 为 String 型的字符串，可以在“应用详情”中查询。
     */
    private String appKeyKey = "appKey";

    /**
     * 资源文件中，连接池最小空闲数 的 key。
     * 其value 为正整数。
     */
    private String poolMinIdleKey = "pool.minIdle";
    /**
     * 资源文件中，连接池最大空闲数 的 key。
     * 其value 为正整数。
     */
    private String poolMaxIdleKey = "pool.maxIdle";
    /**
     * 资源文件中，连接池最大总数 的 key。
     * 其value 为正整数。
     */
    private String poolMaxTotalKey = "pool.maxTotal";


    /**
     * 连接前后缀
     * @param propkey 当前属性key
     * @return 连接后的完整key
     */
    public String concatPrefixAndSuffix(String propkey) {
        if (propkey==null || propkey.trim().length()==0) {
            return null;
        }

        StringBuilder data = new StringBuilder();
        if (this.getPrefix()!=null) {
            data.append(this.getPrefix());
        }
        data.append(propkey);
        if (this.getSuffix()!=null) {
            data.append(this.getSuffix());
        }

        return data.toString();
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRedisTypeKey() {
        return redisTypeKey;
    }

    public void setRedisTypeKey(String redisTypeKey) {
        this.redisTypeKey = redisTypeKey;
    }

    public String getAppIdKey() {
        return appIdKey;
    }

    public void setAppIdKey(String appIdKey) {
        this.appIdKey = appIdKey;
    }

    public String getAppKeyKey() {
        return appKeyKey;
    }

    public void setAppKeyKey(String appKeyKey) {
        this.appKeyKey = appKeyKey;
    }

    public String getPoolMinIdleKey() {
        return poolMinIdleKey;
    }

    public void setPoolMinIdleKey(String poolMinIdleKey) {
        this.poolMinIdleKey = poolMinIdleKey;
    }

    public String getPoolMaxIdleKey() {
        return poolMaxIdleKey;
    }

    public void setPoolMaxIdleKey(String poolMaxIdleKey) {
        this.poolMaxIdleKey = poolMaxIdleKey;
    }

    public String getPoolMaxTotalKey() {
        return poolMaxTotalKey;
    }

    public void setPoolMaxTotalKey(String poolMaxTotalKey) {
        this.poolMaxTotalKey = poolMaxTotalKey;
    }
}
