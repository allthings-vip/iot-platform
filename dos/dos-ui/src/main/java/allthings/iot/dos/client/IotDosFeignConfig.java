package allthings.iot.dos.client;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author tyf
 * @date 2019/07/08 14:31:34
 */
@Configuration
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients
public class IotDosFeignConfig {
}
