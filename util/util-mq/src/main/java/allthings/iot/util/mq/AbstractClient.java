package allthings.iot.util.mq;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import allthings.iot.util.misc.NetUtils;

import java.util.Properties;

/**
 * @author :  sylar
 * @FileName :  AbstractClient
 * @CreateDate :  2017/11/7
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip   All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractClient implements IClient {

    protected String brokers;
    protected String groupId = "DEFAULT";
    protected String clientId = NetUtils.getHostMac();
    protected Properties properties = new Properties();

    @Override
    public void setBasicParameter(String brokers, String groupId, String clientId) {
        this.brokers = brokers;

        if (!Strings.isNullOrEmpty(groupId)) {
            this.groupId = groupId;
        }

        if (!Strings.isNullOrEmpty(clientId)) {
            this.clientId = clientId;
        }

        Preconditions.checkNotNull(this.brokers, "brokers is null");
        Preconditions.checkNotNull(this.groupId, "groupId is null");
        Preconditions.checkNotNull(this.clientId, "clientId is null");
    }

    @Override
    public void setProperties(Properties properties) {
        if (null != properties) {
            this.properties.putAll(properties);
        }
    }
}
