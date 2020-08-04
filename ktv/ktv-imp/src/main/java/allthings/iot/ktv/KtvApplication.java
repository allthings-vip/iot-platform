package allthings.iot.ktv;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  luhao
 * @FileName :  KtvApplication
 * @CreateDate :  2018/1/9
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
public class KtvApplication {
    public static void main(String[] args) {
        SpringApplication.run(KtvApplication.class, args);
    }
}
