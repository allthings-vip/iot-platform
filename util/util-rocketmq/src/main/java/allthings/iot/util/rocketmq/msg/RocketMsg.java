package allthings.iot.util.rocketmq.msg;

import com.aliyun.openservices.ons.api.Message;
import com.google.common.base.Joiner;
import allthings.iot.util.rocketmq.Util;

import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class RocketMsg {
    private String topic;
    private String tags;
    private String keys;
    private String content;
    private String targetClientId;  //for mqtt only
    private Object ext;

    public RocketMsg(String topic) {
        this.topic = topic;
    }

    public RocketMsg(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public RocketMsg(String topic, String tags, String keys, String content) {
        this.topic = topic;
        this.tags = tags;
        this.keys = keys;
        this.content = content;
    }

    public Message getOnsMessage() {
        return new Message(topic, tags, keys, content.getBytes());
    }

    public com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.Message getOwnMessage() {
        return new com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.Message(topic, tags, keys,
                content.getBytes());
    }

    /**
     * P2P
     * 如果发送P2P消息，二级Topic必须是“p2p”，三级Topic是目标的ClientID
     * 此处设置的三级Topic需要是接收方的ClientID
     * <p>
     * notice
     * 消息发送到某个主题Topic，所有订阅这个Topic的设备都能收到这个消息。
     * 遵循MQTT的发布订阅规范，Topic也可以是多级Topic。此处设置了发送到二级Topic
     */
    public String getMqttTopic() {
        return targetClientId != null ? String.format("%s/p2p/%s", topic, targetClientId)
                : String.format("%s/notice/", topic);
    }

    public <T> T getContentObj(Class<T> clazz) {
        return Util.string2Pojo(content, clazz);
    }

    public void setTagList(List<String> tagList) {
        setTags(Joiner.on("||").skipNulls().join(tagList));
    }

    public void setKeyList(List<String> keyList) {
        setKeys(Joiner.on("||").skipNulls().join(keyList));
    }

    @Override
    public String toString() {
        return Util.pojo2String(this);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetClientId() {
        return targetClientId;
    }

    public void setTargetClientId(String targetClientId) {
        this.targetClientId = targetClientId;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }
}
