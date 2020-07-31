package allthings.iot.dos.model.oauth2;


import allthings.iot.dos.model.AbstractIotDosModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotOauthClientDetails
 * @CreateDate :  2018-11-14
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
@Entity
@Table(name = "iot_oauth_client_details")
public class IotOauthClientDetails extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iot_oauth_client_details_id' ", name = "iot_oauth_client_details_id")
    private Long iotOauthClientDetailsId;
    @Column(columnDefinition = "varchar(500) comment 'client_id' ", nullable = false, name = "client_id")
    private String clientId;
    @Column(columnDefinition = "varchar(500) comment '资源id列表' ", nullable = false, name = "resource_ids")
    private String resourceIds;
    @Column(columnDefinition = "varchar(500) comment 'client_secret' ", nullable = false, name = "client_secret")
    private String clientSecret;
    @Column(columnDefinition = "varchar(500) comment 'scope' ", nullable = false, name = "scope")
    private String scope;
    @Column(columnDefinition = "varchar(500) comment '授权类型' ", nullable = false, name = "authorized_grant_types")
    private String authorizedGrantTypes;
    @Column(columnDefinition = "varchar(500) comment 'web_server_redirect_uri' ", nullable = false, name =
            "web_server_redirect_uri")
    private String webServerRedirectUri;
    @Column(columnDefinition = "varchar(500) comment 'authorities' ", nullable = false, name = "authorities")
    private String authorities;
    @Column(columnDefinition = "int(11) comment 'access_token_validity' ", name = "access_token_validity")
    private Integer accessTokenValidity;
    @Column(columnDefinition = "int(11) comment 'refresh_token_validity' ", name = "refresh_token_validity")
    private Integer refreshTokenValidity;
    @Column(columnDefinition = "varchar(500) comment 'additional_information' ", nullable = false, name =
            "additional_information")
    private String additionalInformation;
    @Column(columnDefinition = "varchar(500) comment 'auto_approve' ", nullable = false, name =
            "auto_approve")
    private String autoApprove;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }


    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }


    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }


    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public void setIotOauthClientDetailsId(Long iotOauthClientDetailsId) {
        this.iotOauthClientDetailsId = iotOauthClientDetailsId;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }


    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }

}
