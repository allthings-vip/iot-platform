package allthings.iot.dos.web.security;

import allthings.iot.dos.api.IotUserService;
import allthings.iot.dos.web.kapthcha.KaptchaService;
import allthings.iot.dos.web.message.IotDosLoggerProducer;
import allthings.iot.dos.web.services.CustomerPasswordEncoder;
import allthings.iot.util.redis.ICentralCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author :  luhao
 * @FileName :  SecurityConfiguration
 * @CreateDate :  2018-5-9
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
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${config.apipath}")
    private String contextPath;

    @Autowired
    private ICentralCacheService iotRedisFactory;

    @Autowired
    private IotUserService iotUserApi;

    @Autowired
    private KaptchaService kaptchaService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IotDosLoggerProducer producer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint()).and().authorizeRequests()
                .antMatchers(contextPath + "/home", "/", contextPath + "/code/get",
                        contextPath + "/kaptcha/get", contextPath + "/logout", contextPath + "/user/save", contextPath +
                                "/csrfTokenServlet", contextPath + "/serviceInfo/realtime**",
                        contextPath + "/serviceInfo/list", contextPath + "/serviceInfo/get**",
                        contextPath + "/serviceInfo/save",
                        contextPath + "/serviceInfo/", contextPath + "/serviceInfo/").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage(contextPath + "/login").permitAll().failureForwardUrl(
                contextPath + "/login")
                .usernameParameter("username").passwordParameter("password").successHandler(
                new SessionAuthenticationSuccessHandler(iotRedisFactory, iotUserApi, kaptchaService,
                        producer))
                .failureHandler(new SessionAuthenticationFailureHandler(iotRedisFactory, kaptchaService)).permitAll()
                .and().logout().logoutUrl(
                contextPath + "/logout").logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .permitAll().and().csrf().disable();
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new CustomerPasswordEncoder());
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }
}
