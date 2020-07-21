package allthings.iot.bb808.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  luhao
 * @FileName :  Bb808DasApplication
 * @CreateDate :  2017/12/21
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
public class Bb808DasApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Bb808DasApplication.class, args);
        Server server = ctx.getBean(Server.class);
        server.start();
    }
}