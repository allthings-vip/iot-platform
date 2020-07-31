package allthings.iot.des.client;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author tyf
 * @date 2019/05/15 15:33:46
 */
@Configuration
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients({"allthings.iot.des.client"})
public class IotDesFeignConfig {
}
