package allthings.iot.util.rocketmq.ons;

/**
 * Created by sylar on 2017/1/6.
 */
public abstract class AbsOnsFactory {
    protected String accessKey;
    protected String secretKey;
    protected String serverEndpoint;

    public AbsOnsFactory(String accessKey, String secretKey, String serverEndpoint) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.serverEndpoint = serverEndpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getServerEndpoint() {
        return serverEndpoint;
    }
}
