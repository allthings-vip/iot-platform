package allthings.iot.util.kafka.msg;

import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * Created by sylar on 2017/1/5.
 */
public interface IKafkaMsgListener {

    /**
     * 处理消息并确认
     * <code>true</code>  处理成功，予以确认
     * <false>false</false> 处理失败，不予确认
     *
     * @param records 原始记录
     * @return
     */
    boolean onProcessAndConfirm(ConsumerRecords<String, String> records);

    void onFaild(Throwable throwable);
}
