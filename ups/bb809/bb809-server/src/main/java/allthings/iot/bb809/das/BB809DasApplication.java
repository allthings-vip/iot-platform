package allthings.iot.bb809.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  luhao
 * @FileName :  BB809DasApplication
 * @CreateDate :  2018/3/13
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
public class BB809DasApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BB809DasApplication.class, args);
        Server server = ctx.getBean(Server.class);
        server.start();
    }

}
