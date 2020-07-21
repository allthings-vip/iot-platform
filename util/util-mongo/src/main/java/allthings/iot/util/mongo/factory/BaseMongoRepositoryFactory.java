//package iot.tf56.util.mongo.factory;
//
//import BaseMongoRepositoryImpl;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
//import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
//import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
//import org.springframework.data.querydsl.QueryDslPredicateExecutor;
//import org.springframework.data.repository.core.RepositoryInformation;
//import org.springframework.data.repository.core.RepositoryMetadata;
//
//import java.io.Serializable;
//
//import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;
//
///**
// * Created by tyf on 2016/5/22.
// */
//public class BaseMongoRepositoryFactory<S, ID extends Serializable> extends MongoRepositoryFactory {
//
//    private final MongoOperations mongoOperations;
//
//    public BaseMongoRepositoryFactory(MongoOperations mongoOperations) {
//        super(mongoOperations);
//        this.mongoOperations = mongoOperations;
//    }
//
//    @Override
//    protected Object getTargetRepository(RepositoryInformation information) {
//
//        Class<?> repositoryInterface = information.getRepositoryInterface();
//        MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
//        if (isQueryDslRepository(repositoryInterface)) {
//            return new QueryDslMongoRepository(entityInformation, mongoOperations);
//        } else {
//            return new BaseMongoRepositoryImpl<S, ID>((MongoEntityInformation<S, ID>) entityInformation, this
// .mongoOperations);
//        }
//    }
//
//    private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
//        return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
//    }
//
//    @Override
//    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//        return isQueryDslRepository(metadata.getRepositoryInterface()) ? QueryDslMongoRepository.class :
// BaseMongoRepositoryImpl.class;
//    }
//
//}
