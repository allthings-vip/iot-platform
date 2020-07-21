package allthings.iot.util.kafka.producer;

import allthings.iot.util.kafka.DmsTopics;
import allthings.iot.util.kafka.Helper;
import org.junit.Test;

/**
 * Created by sylar on 2017/3/10.
 */
public class SimpleProcuderTest {

    //    final static String Topic = "test2";
    final static String Topic = DmsTopics.getTopicWhenPublish("vehic", "123456789");

    @Test
    public void test() throws Exception {
        Helper.produce(Topic, "PID");
    }
}