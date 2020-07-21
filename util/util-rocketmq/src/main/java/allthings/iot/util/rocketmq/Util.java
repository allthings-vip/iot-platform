package allthings.iot.util.rocketmq;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;

/**
 * Created by sylar on 2017/1/6.
 */
public class Util {

    public static String bytes2String(byte[] bytes) {
        return new String(bytes, Charsets.UTF_8);
    }

    public static <POJO> String pojo2String(POJO pojo) {
        return JSON.toJSONString(pojo);
    }

    public static <POJO> POJO string2Pojo(String json, Class<POJO> clazz) {
        return JSON.parseObject(json, clazz);
    }

}
