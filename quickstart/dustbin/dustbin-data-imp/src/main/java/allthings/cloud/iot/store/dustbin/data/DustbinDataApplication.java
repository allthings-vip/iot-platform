package tf56.cloud.iot.store.dustbin.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  sylar
 * @FileName :  DustbinDataApplication
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@SpringBootApplication
@ComponentScan(basePackages = {"allthings.iot", "tf56.cloud.iot"})
public class DustbinDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DustbinDataApplication.class, args);
    }
}
