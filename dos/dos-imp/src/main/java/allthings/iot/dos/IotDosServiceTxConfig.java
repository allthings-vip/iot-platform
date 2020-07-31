package allthings.iot.dos;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @author :  luhao
 * @FileName :  IovServiceTxConfig
 * @CreateDate :  2018-9-26
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
@Configuration
public class IotDosServiceTxConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.allthings.iot.dos.service.impl.*.*(..)) || " +
            "execution(* com.allthings.iot.dos.biz.oauth2.impl.*.*(..)) || execution(* com.allthings.iot.dos.open.biz.impl.*.*(" +
            "..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        DefaultTransactionAttribute txAttrRequired = new DefaultTransactionAttribute();
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute txAttrRequiredReadonly = new DefaultTransactionAttribute();
        txAttrRequiredReadonly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrRequiredReadonly.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        source.addTransactionalMethod("save*", txAttrRequired);
        source.addTransactionalMethod("delete*", txAttrRequired);
        source.addTransactionalMethod("update*", txAttrRequired);
        source.addTransactionalMethod("get*", txAttrRequiredReadonly);
        source.addTransactionalMethod("query*", txAttrRequiredReadonly);
        source.addTransactionalMethod("find*", txAttrRequiredReadonly);
        source.addTransactionalMethod("list*", txAttrRequiredReadonly);
        source.addTransactionalMethod("load*", txAttrRequiredReadonly);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
