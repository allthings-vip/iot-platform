package allthings.iot.dos;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author :  luhao
 * @FileName :  IotDosBizApplication
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
@SpringCloudApplication
@EnableAspectJAutoProxy
@EnableScheduling
@ComponentScan(basePackages = {"allthings.iot"})
public class DosApplication {
    public static void main(String[] args) {
        SpringApplication.run(DosApplication.class, args);
    }
}
