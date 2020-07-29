package allthings.iot.dss;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tyf
 * @date 2019/03/29 9:47
 */
@SpringCloudApplication
@ComponentScan({"allthings.iot"})
public class DssApplication {
    public static void main(String[] args) {
        SpringApplication.run(DssApplication.class, args);
    }
}
