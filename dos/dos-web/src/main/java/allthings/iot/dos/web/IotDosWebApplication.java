package allthings.iot.dos.web;

import allthings.iot.dos.web.security.CustomLogoutSuccessHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author :  luhao
 * @FileName :  IotDosWebApplication
 * @CreateDate :  2018/4/24
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@EnableScheduling
@SpringCloudApplication
@ComponentScan({"allthings.iot"})
public class IotDosWebApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(IotDosWebApplication.class, args);
        CustomLogoutSuccessHandler.setApplicationContext(context);
    }
}