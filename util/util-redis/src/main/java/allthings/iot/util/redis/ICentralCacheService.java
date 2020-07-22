package allthings.iot.util.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :  sylar
 * @FileName :  ICentralCacheService
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
public interface ICentralCacheService {
    /**
     * common
     */
    /**
     * 是否包含key
     *
     * @param key
     * @return
     */
    boolean containsKey(String key);

    /**
     * 移除key
     *
     * @param key
     */
    void removeObject(String key);

    /**
     * 批量移除key
     *
     * @param keys
     */
    void removeObjects(Collection<String> keys);

    /**
     * Value
     */
    /**
     * 获取指定clazz的对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getObject(String key, Class<T> clazz);

    /**
     * 存储对象
     *
     * @param key
     * @param t
     * @param <T>
     */
    <T> void putObject(String key, T t);

    /**
     * 存储对象并指定过期时间
     *
     * @param key
     * @param t
     * @param expireTime
     * @param <T>
     */
    <T> void putObjectWithExpireTime(String key, T t, long expireTime);

    /**
     * Map
     */
    /**
     * 存储Map对象，并指定过期时间
     *
     * @param key
     * @param fieldName
     * @param t
     * @param expireTime
     * @param <T>
     */
    <T> void putMapValue(String key, String fieldName, T t, long expireTime);

    /**
     * 存储Map对象
     *
     * @param key
     * @param fieldName
     * @param t
     * @param <T>
     */
    <T> void putMapValue(String key, String fieldName, T t);

    /**
     * 获取Map中的某个field的值，并转换成指定的clazz对象
     *
     * @param key
     * @param fieldName
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapField(String key, String fieldName, Class<T> clazz);

    /**
     * 更新指定key的field的内容
     *
     * @param key
     * @param fieldName
     * @param t
     * @param <T>
     */
    <T> void updateMapField(String key, String fieldName, T t);

    /**
     * 批量更新指定key的field的内容
     *
     * @param key
     * @param fields
     * @param <T>
     */
    <T> void updateMapFields(String key, Map<String, T> fields);

    /**
     * 移除指定key的相应field
     *
     * @param key
     * @param fieldName
     */
    void removeMapField(String key, String fieldName);

    /**
     * 获取key的所有的field的内容
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Map<String, T> getAll(String key, Class<T> clazz);

    /**
     * Set
     */
    /**
     * 将内容存储到set
     *
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    <T> long putObjectToSet(String key, T t);

    /**
     * 从set中获取
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Set<T> getObjectsFromSet(String key, Class<T> clazz);

    /**
     * 从set中移除
     *
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    <T> long removeObjectFromSet(String key, T t);

    /**
     * ZSet
     */
    /**
     * 将内容存储到ZSet并设置score
     *
     * @param key
     * @param t
     * @param score
     * @param <T>
     * @return
     */
    <T> boolean putObjectToZSet(String key, T t, double score);

    /**
     * 将内容存储到ZSet并设置score和超时时间
     *
     * @param key
     * @param t
     * @param score
     * @param expireTime
     * @param <T>
     */
    <T> void putObjectToZSetWithExpireTime(String key, T t, double score, long expireTime);

    /**
     * 从zSet获取内容
     *
     * @param key
     * @param minScore
     * @param maxScore
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Set<T> getObjectsFromZSet(String key, double minScore, double maxScore, Class<T> clazz);

    /**
     * 从zSet移除 minScore - maxScore 内容
     *
     * @param key
     * @param minScore
     * @param maxScore
     */
    void removeObjectFromZSet(String key, double minScore, double maxScore);

    /**
     * 从zSet移除内容
     *
     * @param key
     * @param t
     * @param <T>
     */
    <T> void removeObjectFromZSet(String key, T t);

    /**
     * List
     */
    /**
     * 将内容放入list并设置过期时间
     *
     * @param key
     * @param t
     * @param limit
     * @param expireTime
     * @param <T>
     */
    <T> void putObjectToListWithLimitAndExpireTime(String key, T t, int limit, long expireTime);

    /**
     * 从list中获取内容
     *
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getObjectsFromList(String key, int start, int end, Class<T> clazz);

    /**
     * 从list中移除内容
     *
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    <T> long removeObjectFormList(String key, T t);

}


