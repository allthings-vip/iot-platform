package allthings.iot.util.kafka.consumer;

import allthings.iot.util.kafka.DmsTopics;
import allthings.iot.util.kafka.Helper;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by sylar on 2017/3/10.
 */
public class SimpleConsumerTest {

    //    final static String Topic = "test2";
    final static String Topic = DmsTopics.getTopicWhenPublish("vehic", "123456789");
    final static Pattern pattern = Pattern.compile("dms.*.*");

    @Test
    public void consumeByTopic() throws Exception {
        Helper.consumeByTopic(Topic, "CID", "GID");
    }


    @Test
    public void consumeByPattern() throws Exception {
        Helper.consumeByPartten(pattern, "CID", "GID");
    }


    @Test
    public void consumeByCustom() throws Exception {
        Integer partition = null;
        Long offsert = null;
        Helper.consumeByCustom(Topic, partition, offsert, "CID", "GID");
    }


}