package allthings.iot.bsj.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  luhao
 * @FileName :  BsjApplication
 * @CreateDate :  2017/12/28
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
public class BsjApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BsjApplication.class, args);
        Server server = ctx.getBean(Server.class);
        server.start();
    }
}
