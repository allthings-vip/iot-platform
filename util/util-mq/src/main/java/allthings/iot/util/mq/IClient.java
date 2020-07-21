package allthings.iot.util.mq;

import java.util.Properties;

/**
 * @author :  sylar
 * @FileName :  IClient
 * @CreateDate :  2017/11/7
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface IClient {

    /**
     * 设置基本参数
     *
     * @param brokers  服务器列表，形如 ip:port,ip:port
     * @param groupId  所属组标识
     * @param clientId 客户端标识
     */
    void setBasicParameter(String brokers, String groupId, String clientId);

    /**
     * 设置额外参数配置
     *
     * @param properties 参数配置
     */
    void setProperties(Properties properties);
}
