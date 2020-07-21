package allthings.iot.util.kafka;

import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * Created by sylar on 2017/3/10.
 */
public class ClusterTest {

    final static String Topic = "TOPIC";
    final static String Group = "GROUP";

    @Test
    public void producer() throws InterruptedException {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Helper.clusterProduce(Topic, 30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Thread.sleep(1000 * 5);
        System.out.println("producer over");
    }


    @Test
    public void consumer() throws InterruptedException {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Helper.clusterConsume(Topic, Group, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread.sleep(1000 * 60);
        System.out.println("ClusterTest over");

    }
}
