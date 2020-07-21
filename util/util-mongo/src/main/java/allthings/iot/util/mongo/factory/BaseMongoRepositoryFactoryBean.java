//package iot.tf56.util.mongo.factory;
//
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
//import org.springframework.data.repository.core.support.RepositoryFactorySupport;
//
//import java.io.Serializable;
//
///**
// * Created by tyf on 2016/5/22.
// */
//public class BaseMongoRepositoryFactoryBean<T extends MongoRepository<S, ID>, S, ID extends Serializable> extends
//        MongoRepositoryFactoryBean<T, S, ID> {
//
//    public BaseMongoRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
//        super(repositoryInterface);
//    }
//
//    @Override
//    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
//        return new BaseMongoRepositoryFactory(operations);
//    }
//}
//
