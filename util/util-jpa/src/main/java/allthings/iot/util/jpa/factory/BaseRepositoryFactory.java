package allthings.iot.util.jpa.factory;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import allthings.iot.util.jpa.impl.BaseRepositoryImpl;

import javax.persistence.EntityManager;

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
public class BaseRepositoryFactory extends JpaRepositoryFactory {

    public BaseRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
                                                                    EntityManager entityManager) {
        return new BaseRepositoryImpl(information.getDomainType(), entityManager);
    }


    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return BaseRepositoryImpl.class;
    }

}
