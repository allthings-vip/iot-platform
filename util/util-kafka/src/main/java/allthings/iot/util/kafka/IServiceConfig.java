package allthings.iot.util.kafka;


import java.util.Map;

/**
 * Created by sylar on 2017/1/5.
 */
public interface IServiceConfig extends Map<String, Object> {

    /**
     * broker server 列表
     * 形如："host1:9092,host2:9092"
     *
     * @return broker server 列表
     */
    String getBootstrapServers();


    /**
     * 客户端标识
     * 建议使用客户端应用名称作为标识，若该应用有多个实例，可加上实例标识，如: appName_01
     *
     * @return 客户端标识
     */
    String getClientId();
}
