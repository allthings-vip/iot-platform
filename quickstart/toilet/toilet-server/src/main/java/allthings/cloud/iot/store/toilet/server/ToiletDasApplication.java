package tf56.cloud.iot.store.toilet.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :  sylar
 * @FileName :  ToiletDasApplication
 * @CreateDate :  2017/11/08
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
@ComponentScan(basePackages={"iot.tf56","me.cloud.iot"})
public class ToiletDasApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ToiletDasApplication.class, args);
        Server server = ctx.getBean(Server.class);
        server.start();
    }
}
