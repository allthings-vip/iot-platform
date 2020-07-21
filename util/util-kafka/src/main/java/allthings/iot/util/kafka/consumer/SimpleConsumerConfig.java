package allthings.iot.util.kafka.consumer;

import allthings.iot.util.kafka.AbsServiceConfig;

/**
 * Created by sylar on 2017/3/9.
 */
public class SimpleConsumerConfig extends AbsServiceConfig implements IConsumerConfig {

    protected String groupId;

    public SimpleConsumerConfig(String bootstrapServers, String clientId, String groupId) {
        super(bootstrapServers, clientId);
        this.groupId = groupId;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

}
