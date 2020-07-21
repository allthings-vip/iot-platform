package allthings.iot.util.mongo;

import allthings.iot.util.mongo.impl.BaseMongoRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
//@ConditionalOnProperty(name = "spring.data.mongodb")
@Configuration
@EnableMongoRepositories(basePackages = "allthings.iot", repositoryBaseClass = BaseMongoRepositoryImpl.class)
public class MongoConfig {
}
