package allthings.iot.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsConfig {
    private String RegionId;

    private String accessKeyId;

    private String accessSecret;


}
