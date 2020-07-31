package allthings.iot.vehicle.client;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-06-26 10:48
 */
@Configuration
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients({"tf56.iot.vehicle.data.feign"})
public class VehicleDataFeignConfig {
}
