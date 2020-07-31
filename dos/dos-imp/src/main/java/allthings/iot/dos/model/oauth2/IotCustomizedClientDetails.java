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
 * @FileName :  IotCustomizedClientDetails
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
@Table(name = "iot_customized_client_details")
public class IotCustomizedClientDetails extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iot_oauth_client_details_id' ", name = "iot_oauth_client_details_id")
    private Long iotCustomizedClientDetailsId;
    @Column(columnDefinition = "varchar(500) comment 'app_id' ", nullable = false, name = "app_id")
    private String appId;
    @Column(columnDefinition = "varchar(500) comment '资源id列表' ", nullable = false, name = "resource_ids")
    private String resourceIds;
    @Column(columnDefinition = "varchar(500) comment 'app_secret' ", nullable = false, name = "app_secret")
    private String appSecret;
    @Column(columnDefinition = "varchar(500) comment 'scope' ", nullable = false, name = "scope")
    private String scope;
    @Column(columnDefinition = "varchar(500) comment '授权类型' ", nullable = false, name = "grant_types")
    private String grantTypes;
    @Column(columnDefinition = "varchar(500) comment 'redirect_url' ", nullable = false, name = "redirect_url")
    private String redirectUrl;
    @Column(columnDefinition = "varchar(500) comment 'authorities' ", nullable = false, name = "authorities")
    private String authorities;
    @Column(columnDefinition = "int(11) comment 'access_token_validity' ", name = "access_token_validity")
    private Long accessTokenValidity;
    @Column(columnDefinition = "int(11) comment 'refresh_token_validity' ", name = "refresh_token_validity")
    private Long refreshTokenValidity;
    @Column(columnDefinition = "varchar(500) comment 'additional_information' ", nullable = false, name =
            "additional_information")
    private String additionalInformation;
    @Column(columnDefinition = "varchar(500) comment 'auto_approve_scopes' ", nullable = false, name =
            "auto_approve_scopes")
    private String autoApproveScopes;


    public Long getIotCustomizedClientDetailsId() {
        return iotCustomizedClientDetailsId;
    }

    public void setIotCustomizedClientDetailsId(Long iotCustomizedClientDetailsId) {
        this.iotCustomizedClientDetailsId = iotCustomizedClientDetailsId;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }


    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }


    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }


    public Long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }


    public Long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }


    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(String autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

}
