package allthings.iot.util.rocketmq;


import com.alibaba.fastjson.JSON;

/**
 * Created by sylar on 2017/1/2.
 */
public class User {
    String id = "123";
    String name = "sylar";
    int age = 12;
    long time = System.currentTimeMillis();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
