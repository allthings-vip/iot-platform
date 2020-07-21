package allthings.iot.util.kafka;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class Util {
    final static String INVALID_BOOTSTRAPSERVERS = "invalid " + ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

    public static void checkBrokerListConfig(String brokerList) {
        Preconditions.checkNotNull(brokerList);
        List<String> list = Splitter.on(",").trimResults().splitToList(brokerList);
        Preconditions.checkState(list.size() >= 1, INVALID_BOOTSTRAPSERVERS + " brokerList:" + brokerList);

        list.forEach(broker -> {
            List<String> tmp = Splitter.on(":").trimResults().splitToList(broker);
            Preconditions.checkState(tmp.size() == 2, INVALID_BOOTSTRAPSERVERS + " broker:" + broker);

            Integer port = null;
            try {
                port = Integer.parseInt(tmp.get(1));
            } catch (Exception e) {
            }

            Preconditions.checkState(port != null && port > 0, INVALID_BOOTSTRAPSERVERS + " broker:" + broker);
        });
    }

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
