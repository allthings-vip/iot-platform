/*
package iot.tf56.common;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import iot.tf56.common.service.impl.CentralCacheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Map;

*/
/**
 * Created by tyf on 2017/7/6/0006.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("iot.tf56")
@SpringBootApplication
public class CentralCacheServiceTest {
    @Autowired
    private CentralCacheService centralCacheService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Before
    public void sout() {
        System.out.println(redisTemplate.getConnectionFactory().getConnection());
    }

    @Test
    public void containsKey() {
        System.out.println(JSON.toJSONString(centralCacheService.containsKey("abc")));
    }

    @Test
    public void removeObject() {
        centralCacheService.removeObject("abc");
    }

    @Test
    public void removeObjects() {
        centralCacheService.removeObjects(Arrays.asList("aaa", "abc", "eda"));
    }

    @Test
    public void getObject() {
        System.out.println(centralCacheService.getObject("abc", String.class));
    }

    @Test
    public void putObject() {
        centralCacheService.putObject("abc", "abcedfghijk");
    }

    @Test
    public void putObjectWithExpireTime() {
        centralCacheService.putObjectWithExpireTime("abc", "abcdfadj", 10);
    }

    @Test
    public void putMapValueWithExpireTime() {
        centralCacheService.putMapValue("das", "field", "central cache service", 10);
    }

    @Test
    public void putMapValue() {
        centralCacheService.putMapValue("abc", "field", "central cache service");
    }

    @Test
    public void getMapField() {
        System.out.println(centralCacheService.getMapField("abc", "field", String.class));
    }

    @Test
    public void updateMapField() {
        centralCacheService.updateMapField("abc", "field", "central cache service update");
    }

    @Test
    public void updateMapFields() {
        Map<String, String> map = Maps.newHashMap();
        map.put("field1", "central cache service update");
        map.put("field", "central cache service update two");
        centralCacheService.updateMapFields("abc", map);
    }

    @Test
    public void removeMapField() {
        centralCacheService.removeMapField("abc", "field1");
    }

    @Test
    public void getAll() {
        System.out.println(centralCacheService.getAll("abc", String.class));
    }

    @Test
    public void getAllString() {
        System.out.println(centralCacheService.getAll("abc"));
    }

    @Test
    public void putObjectToSet() {
        centralCacheService.putObjectToSet("abc", "putObjectToSet");
    }

    @Test
    public void removeObjectFromSet() {
        centralCacheService.removeObjectFromSet("abc", "putObjectToSet");
    }

    @Test
    public void putObjectToZSet() {
        centralCacheService.putObjectToZSet("set", "putObjectToZSet", 1);
    }

    @Test
    public void putObjectToZSetWithExpireTime() {
        centralCacheService.putObjectToZSetWithExpireTime("zset", String.class, 1, 10);
    }

    @Test
    public void getObjectsFromZSet() {
        System.out.println(centralCacheService.getObjectsFromZSet("set", 0, 2, String.class));
    }

    @Test
    public void removeObjectFromZSet() {
        centralCacheService.removeObjectFromZSet("set", 0, 2);
    }

    @Test
    public void removeObjectFromZSetString() {
        centralCacheService.removeObjectFromZSet("set", "putObjectToZSet");
    }

    @Test
    public void putObjectToListWithLimitAndExpireTime() {
        centralCacheService.putObjectToListWithLimitAndExpireTime("list", "putObjectToListWithLimitAndExpireTime", 1,
                10);
    }

    @Test
    public void getObjectsFromList() {
        System.out.println(centralCacheService.getObjectsFromList("list", 0, 3, String.class));
    }

    @Test
    public void removeObjectFormList() {
        centralCacheService.removeObjectFormList("list", String.class);
    }

}
*/
