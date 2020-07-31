package allthings.iot.ktv.client;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author tyf
 * @date 2019/05/09 18:46:22
 */
@Configuration
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients({"allthings.iot.ktv.client"})
public class KtvDataClientConfig {
}
