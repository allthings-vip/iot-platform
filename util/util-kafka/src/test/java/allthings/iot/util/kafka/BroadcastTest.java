package allthings.iot.util.kafka;

import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * Created by sylar on 2017/3/10.
 */
public class BroadcastTest {

    final static String Topic = "TOPIC";
    final static String Group = "GROUP";

    @Test
    public void test() throws Exception {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Helper.clusterProduce(Topic, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Helper.broadcastConsume(Topic, Group, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread.sleep(1000 * 60);
        System.out.println("BroadcastTest over");
    }
}
