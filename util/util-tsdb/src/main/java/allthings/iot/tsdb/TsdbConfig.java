package allthings.iot.tsdb;

import com.baidubce.Protocol;

/**
 * @author :  luhao
 * @FileName :  TsdbConfig
 * @CreateDate :  2017/6/21
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
public class TsdbConfig {
    private String accessKey;
    private String accessSecret;
    private String endpoint;
    private Protocol protocol;

    public TsdbConfig(String accessKey, String accessSecret, String endpoint) {
        this(accessKey, accessSecret, endpoint, Protocol.HTTP);
    }

    public TsdbConfig(String accessKey, String accessSecret, String endpoint, Protocol protocol) {
        if (protocol == null) {
            protocol = Protocol.HTTP;
        }

        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.endpoint = endpoint;
        this.protocol = protocol;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
