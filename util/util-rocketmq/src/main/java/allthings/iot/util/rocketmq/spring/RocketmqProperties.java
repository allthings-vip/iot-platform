//package allthings.iot.util.rocketmq.spring;
//
//import com.google.common.base.Preconditions;
//import com.google.common.base.Strings;
//import IFactory;
//import RocketMQUtil;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
///**
// *
// * Created by sylar on 16/5/18.
// */
//@ConfigurationProperties(prefix = "rocketmq")
//public class RocketmqProperties {
//
//    /**
//     * 阿里云的服务url 或 rocketmq 的名称服务地址
//     */
//    protected String server;
//
//    /**
//     * 如果是阿里云ONS， accessKey 与 secretKey 须配置
//     * 如果是原生的rocketmq, accessKey 与 secretKey 须置空
//     */
//    protected String accessKey;
//    protected String secretKey;
//
//
//    public IFactory getRocketmqFactory() {
//        Preconditions.checkNotNull(server, "rocketmq.server property is null");
//
//        if (Strings.isNullOrEmpty(accessKey) || Strings.isNullOrEmpty(secretKey)) {
//            return RocketMQUtil.createOnsTcpFactory(accessKey, secretKey, server);
//        } else {
//            return RocketMQUtil.createOwnFactory(server);
//        }
//    }
//
//    public String getServer() {
//        return server;
//    }
//
//    public void setServer(String server) {
//        this.server = server;
//    }
//
//    public String getAccessKey() {
//        return accessKey;
//    }
//
//    public void setAccessKey(String accessKey) {
//        this.accessKey = accessKey;
//    }
//
//    public String getSecretKey() {
//        return secretKey;
//    }
//
//    public void setSecretKey(String secretKey) {
//        this.secretKey = secretKey;
//    }
//}
