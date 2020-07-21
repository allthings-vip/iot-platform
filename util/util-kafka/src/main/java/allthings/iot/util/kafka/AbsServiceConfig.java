package allthings.iot.util.kafka;

import java.util.HashMap;

/**
 * Created by sylar on 2017/3/9.
 */
public class AbsServiceConfig extends HashMap<String, Object> implements IServiceConfig {

    protected String bootstrapServers;
    protected String clientId;

    public AbsServiceConfig(String bootstrapServers, String clientId) {
        this.bootstrapServers = bootstrapServers;
        this.clientId = clientId;

//        String confPath = "/usr/local/kafka/config/kafka_client_jaas.conf";
//        System.setProperty("java.security.auth.login.config", confPath);
//        this.put("security.protocol", "SASL_PLAINTEXT");
//        this.put("sasl.mechanism", "PLAIN");
    }

    @Override
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    @Override
    public String getClientId() {
        return clientId;
    }
}