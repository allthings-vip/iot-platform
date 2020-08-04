package allthings.iot.dos.open;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @author :  luhao
 * @FileName :  com.tf56.iot.dos.IotDosOpenWebApplication
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
@ComponentScan({"allthings.iot"})
@EnableOAuth2Client
@EnableScheduling
public class IotDosOpenWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotDosOpenWebApplication.class, args);
    }
}