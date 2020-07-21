package allthings.iot.gbt32960.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  luhao
 * @FileName :  Gbt32960Application
 * @CreateDate :  2018/1/29
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
@SpringBootApplication
@ComponentScan("allthings.iot")
public class Gbt32960Application {
    public static void main(String[] args) {
        SpringApplication.run(Gbt32960Application.class, args);
    }
}
