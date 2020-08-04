package allthings.iot.dos.open;

import allthings.iot.dos.open.security.oauth2.AuthExceptionEntryPoint;
import allthings.iot.dos.open.security.oauth2.handler.CustomOauthExceptionRenderer;
import allthings.iot.dos.open.security.oauth2.handler.CustomWebResponseExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @author :  luhao
 * @FileName :  com.tf56.iot.dos.WebSecurityConfigurer
 * @CreateDate :  2018/4/24
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
@EnableResourceServer
public class WebSecurityConfigurer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http.authorizeRequests().antMatchers("/oauth/token**", "/health/check**").permitAll().anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(getClientAuthenticationEntryPoint());
        // @formatter:on
    }

    @Bean(name = "exceptionRenderer")
    public CustomOauthExceptionRenderer getCustomExceptionRenderer() {
        CustomOauthExceptionRenderer customExceptionRenderer = new CustomOauthExceptionRenderer();
        return customExceptionRenderer;
    }

    @Bean(name = "clientAuthenticationEntryPoint")
    public AuthExceptionEntryPoint getClientAuthenticationEntryPoint() {
        AuthExceptionEntryPoint authExceptionEntryPoint = new AuthExceptionEntryPoint();
        authExceptionEntryPoint.setExceptionTranslator(customWebResponseExceptionTranslator());
        authExceptionEntryPoint.setExceptionRenderer(getCustomExceptionRenderer());
        return authExceptionEntryPoint;
    }

    @Bean
    public WebResponseExceptionTranslator customWebResponseExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(getClientAuthenticationEntryPoint());
    }

}
