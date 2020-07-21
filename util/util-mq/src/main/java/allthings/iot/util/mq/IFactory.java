package allthings.iot.util.mq;

/**
 * @author :  sylar
 * @FileName :  IFactory
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
public interface IFactory {

    /**
     * 创建生产者
     *
     * @param provider
     * @return
     */
    IProducer createProducer(Provider provider);

    /**
     * 创建消费者
     *
     * @param provider
     * @return
     */
    IConsumer createConsumer(Provider provider);
}
