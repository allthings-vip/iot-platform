package allthings.iot.util.redis.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Primitives;
import allthings.iot.util.json.JsonValidator;
import allthings.iot.util.redis.ICentralCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author :  sylar
 * @FileName :  CentralCacheServiceImpl
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
public class CentralCacheServiceImpl implements ICentralCacheService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean containsKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public void removeObject(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void removeObjects(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        if (containsKey(key)) {
            return decode(valOps(key).get(), clazz);
        } else {
            return null;
        }
    }

    @Override
    public <T> void putObject(String key, T t) {
        if (t == null) {
            return;
        }

        String value = encode(t);
        valOps(key).set(value);
    }

    @Override
    public <T> void putObjectWithExpireTime(String key, T t, long expireTime) {
        if (t == null) {
            return;
        }
        String value = encode(t);
        valOps(key).set(value, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public <T> void putMapValue(String key, String fieldName, T t, long expireTime) {
        String value = encode(t);
        BoundHashOperations<String, String, String> ops = mapOps(key);
        ops.put(fieldName, value);
        if (expireTime > 0) {
            ops.expire(expireTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public <T> void putMapValue(String key, String fieldName, T t) {
        putMapValue(key, fieldName, t, 0);
    }

    @Override
    public <T> T getMapField(String key, String fieldName, Class<T> clazz) {
        return decode(mapOps(key).get(fieldName), clazz);
    }

    @Override
    public <T> void updateMapField(String key, String fieldName, T t) {
        mapOps(key).put(fieldName, encode(t));
    }

    @Override
    public <T> void updateMapFields(String key, Map<String, T> fields) {
        Map<String, String> map = Maps.newHashMap();
        for (Map.Entry<String, T> field : fields.entrySet()) {
            map.put(field.getKey(), encode(field.getValue()));
        }
        mapOps(key).putAll(map);
    }

    @Override
    public void removeMapField(String key, String fieldName) {
        mapOps(key).delete(fieldName);
    }

    @Override
    public <T> Map<String, T> getAll(String key, Class<T> clazz) {
        Map<String, String> map = mapOps(key).entries();
        Map<String, T> res = Maps.newHashMap();
        for (Map.Entry<String, String> field : map.entrySet()) {
            res.put(field.getKey(), decode(field.getValue(), clazz));
        }
        return res;
    }

    @Override
    public <T> long putObjectToSet(String key, T t) {
        return setOps(key).add(encode(t));
    }

    @Override
    public <T> Set<T> getObjectsFromSet(String key, Class<T> clazz) {
        Set<String> set = setOps(key).members();
        Set<T> res = Sets.newHashSet();
        for (String s : set) {
            res.add(decode(s, clazz));
        }
        return res;
    }

    @Override
    public <T> long removeObjectFromSet(String key, T t) {
        return setOps(key).remove(encode(t));
    }

    @Override
    public <T> boolean putObjectToZSet(String key, T t, double score) {
        return zsetOps(key).add(encode(t), score);
    }

    @Override
    public <T> void putObjectToZSetWithExpireTime(String key, T t, double score, long expireTime) {
        BoundZSetOperations<String, String> zsetOps = zsetOps(key);
        zsetOps.add(encode(t), score);
        if (expireTime > 0) {
            zsetOps.expire(expireTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public <T> Set<T> getObjectsFromZSet(String key, double minScore, double maxScore, Class<T> clazz) {
        Set<String> values = zsetOps(key).rangeByScore(minScore, maxScore);
        Set<T> res = Sets.newHashSet();
        for (String value : values) {
            res.add(decode(value, clazz));
        }
        return res;
    }

    @Override
    public void removeObjectFromZSet(String key, double minScore, double maxScore) {
        zsetOps(key).removeRangeByScore(minScore, maxScore);
    }

    @Override
    public <T> void removeObjectFromZSet(String key, T t) {
        zsetOps(key).remove(encode(t));
    }

    @Override
    public <T> void putObjectToListWithLimitAndExpireTime(String key, T t, int limit, long expireTime) {
        stringRedisTemplate.execute(new SessionCallback<Boolean>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                BoundListOperations<String, String> ops = lstOps(key);
                ops.leftPush(encode(t));
                if (limit > 0) {
                    ops.trim(0, limit);
                }
                if (expireTime > 0) {
                    ops.expire(expireTime, TimeUnit.SECONDS);
                }
                operations.exec();
                return true;
            }
        });
    }

    @Override
    public <T> List<T> getObjectsFromList(String key, int start, int end, Class<T> clazz) {
        List<String> values = lstOps(key).range(start, end);
        List<T> res = Lists.newArrayList();
        for (String value : values) {
            res.add(decode(value, clazz));
        }
        return res;
    }

    @Override
    public <T> long removeObjectFormList(String key, T t) {
        return lstOps(key).remove(0, encode(t));
    }

    //==================================================================================================================

    BoundValueOperations<String, String> valOps(String key) {
        return stringRedisTemplate.boundValueOps(key);
    }

    BoundHashOperations<String, String, String> mapOps(String key) {
        return stringRedisTemplate.boundHashOps(key);
    }

    BoundListOperations<String, String> lstOps(String key) {
        return stringRedisTemplate.boundListOps(key);
    }

    BoundSetOperations<String, String> setOps(String key) {
        return stringRedisTemplate.boundSetOps(key);
    }

    BoundZSetOperations<String, String> zsetOps(String key) {
        return stringRedisTemplate.boundZSetOps(key);
    }

    String encode(Object obj) {
        if (obj == null) {
            return null;
        } else {
            Class<?> clazz = obj.getClass();
            String value;
            if (clazz.isPrimitive() || Primitives.isWrapperType(clazz) || obj instanceof String) {
                //值类型或字符串类型
                value = String.valueOf(obj);
            } else {
                //pojo
                value = JSON.toJSONString(obj);
            }

            return value;
        }
    }

    <T> T decode(String text, Class<T> clazz) {
        if (text == null) {
            return null;
        }

        JsonValidator jsonValidator = new JsonValidator();
        if (jsonValidator.validate(text)) {
            return JSON.parseObject(text, clazz);
        } else {
            return (T) text;
        }
    }

}
