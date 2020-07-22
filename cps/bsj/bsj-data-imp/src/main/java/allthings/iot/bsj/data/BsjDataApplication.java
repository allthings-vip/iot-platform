package allthings.iot.bsj.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  luhao
 * @FileName :  BsjDataApplication
 * @CreateDate :  2018/1/11
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
@ComponentScan("allthings.iot")
public class BsjDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(BsjDataApplication.class, args);
    }
}
