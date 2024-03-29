package allthings.iot.des;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author tyf
 * @date 2019/03/04 17:29
 */
@SpringCloudApplication
@ComponentScan({"allthings.iot"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = {"allthings.iot.des.mapper"})
public class IotDesApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotDesApplication.class, args);
    }
}
