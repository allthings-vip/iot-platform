package allthings.iot.util.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "allthings.iot.sms")
public class SmsProperties {
    /**
     * 服务区域编号
     */
    private String RegionId;

    /**
     * 短信服务认证秘钥
     */
    private String accessKeyId;

    /**
     * 短信服务认证秘钥
     */
    private String accessSecret;

    /**
     * 验证码有效期
     * 单位：分钟
     */
    private Integer validPeriod;

    /**
     * 接口地址
     */
    private String domain;

    /**
     * 版本
     */
    private String version;

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public Integer getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(Integer validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
