package allthings.iot.dos.web.kapthcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-22 09:58
 */
@Component
public class KaptchaConfig {
    @Bean(name = "iotDosKaptcha")
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "35");
        properties.setProperty("kaptcha.textproducer.font.size", "26");
        properties.setProperty("kaptcha.textproducer.font.names", "MingLiU");
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.setProperty("kaptcha.noise.color", "gray");
        properties.setProperty("kaptcha.background.clear.from", "white");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
