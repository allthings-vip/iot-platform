package allthings.iot.util.jpa;

import allthings.iot.util.jpa.factory.BaseRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@ConditionalOnProperty(name = "spring.datasource")
@Configuration
@EnableJpaRepositories(basePackages = {"allthings.iot"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class JpaConfig {
}
