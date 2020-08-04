package allthings.iot.dos.open;

import allthings.iot.dos.client.open.IotOauth2Api;
import allthings.iot.dos.open.security.oauth2.AuthExceptionEntryPoint;
import allthings.iot.dos.open.security.oauth2.RpcApprovalStore;
import allthings.iot.dos.open.security.oauth2.RpcAuthorizationCodeServices;
import allthings.iot.dos.open.security.oauth2.RpcClientDetailsService;
import allthings.iot.dos.open.security.oauth2.RpcTokenStore;
import allthings.iot.dos.open.security.oauth2.handler.CustomAccessDeniedHandler;
import allthings.iot.dos.open.security.oauth2.handler.CustomWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author :  luhao
 * @FileName :  com.tf56.iot.dos.AuthorizationServerConfiguration
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
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private IotOauth2Api iotOauth2Api;


    @Autowired
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
        customAccessDeniedHandler.setExceptionTranslator(customWebResponseExceptionTranslator);
        return customAccessDeniedHandler;
    }

    @Bean
    public RpcClientDetailsService rpcClientDetailsService() {
        return new RpcClientDetailsService(iotOauth2Api);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new RpcAuthorizationCodeServices(iotOauth2Api);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.checkTokenAccess("permitAll()");
        oauthServer.accessDeniedHandler(customAccessDeniedHandler());
        oauthServer.authenticationEntryPoint(authExceptionEntryPoint);
        oauthServer.passwordEncoder(new CustomPasswordEncoder());
        oauthServer.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(rpcClientDetailsService());
    }

    @Bean
    public TokenStore tokenStore() {
        return new RpcTokenStore(iotOauth2Api);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new RpcApprovalStore(iotOauth2Api);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.approvalStore(approvalStore())
                .authorizationCodeServices(authorizationCodeServices())
                .tokenStore(tokenStore());

    }
}
