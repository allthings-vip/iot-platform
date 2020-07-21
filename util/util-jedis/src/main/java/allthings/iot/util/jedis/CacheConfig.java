package allthings.iot.util.jedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :  luhao
 * @FileName :  CacheConfig
 * @CreateDate :  2017/11/20
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
public class CacheConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public CacheCloudRedisFactory getCache() throws Exception {
        return new CacheCloudRedisFactory(String.format("config/application-%s.properties", activeProfile));
    }
}
