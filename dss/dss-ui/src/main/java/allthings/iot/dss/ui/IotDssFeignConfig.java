package allthings.iot.dss.ui;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author tyf
 * @date 2019/05/06 14:52:23
 */
@Configuration
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients({"allthings.iot.dss.ui"})
public class IotDssFeignConfig {
}
