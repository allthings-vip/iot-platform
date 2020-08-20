package allthings.iot.dos.web;

import allthings.iot.dos.web.servlet.CsrfTokenServlet;
import allthings.iot.dos.web.xss.DefaultJsonDeserializer;
import allthings.iot.dos.web.xss.DefaultJsonSerializer;
import allthings.iot.util.redis.ICentralCacheService;
import allthings.iot.util.rocketmq.IFactory;
import allthings.iot.util.rocketmq.RocketMQUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.toolkit.security.AbstractCsrfTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotDosWebConfig
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
@EnableRedisHttpSession
public class IotDosWebConfig implements WebMvcConfigurer {

    @Value("${http.url.whiteUrlList}")
    private String whiteUrlList;
    @Value("${crsf.whiteList}")
    private String csrfWhiteList;
    @Value("${iot.rocketmq.brokers}")
    private String brokers;

    @Autowired
    private ICentralCacheService cacheConfig;


    /**
     * csrf过滤器
     */
    @Bean
    public FilterRegistrationBean csrfFilterRegistrationBean() throws Exception {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CsrfTokenServlet(cacheConfig));
        filterRegistrationBean.setOrder(4);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put(AbstractCsrfTokenFilter.CSRF_EXLUCDE_URI, csrfWhiteList);
        filterRegistrationBean.setInitParameters(initParameters);

        return filterRegistrationBean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new DefaultJsonDeserializer());
        module.addSerializer(String.class, new DefaultJsonSerializer());
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        mapper.registerModule(module);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);

        converters.add(0, converter);
    }


    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        return (WebServerFactoryCustomizer<ConfigurableWebServerFactory>) factory -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
            ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
            ErrorPage error417Page = new ErrorPage(HttpStatus.EXPECTATION_FAILED, "/417");
            factory.setErrorPages(Sets.newHashSet(error404Page, error500Page, error400Page, error417Page));
        };
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("DOS-SESSION");
        serializer.setCookiePath("/");
        return serializer;
    }

    @Bean
    public ServletRegistrationBean csrfServlet() throws Exception {
        ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(new CsrfTokenServlet(cacheConfig),
                        "/csrfTokenServlet");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public IFactory getFactory() {
        return RocketMQUtil.createOwnFactory(brokers);
    }
}
