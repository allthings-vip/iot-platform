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
 * @FileName :  IotOauthRefreshToken
 * @CreateDate :  2018/04/25
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
@Table(name = "iot_oauth_refresh_token")
public class IotOauthRefreshToken extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iot_oauth_refresh_token_id' ", name = "iot_oauth_refresh_token_id")
    private Long iotOauthRefreshTokenId;
    @Column(columnDefinition = "varchar(500) comment 'token_id' ", nullable = false, name = "token_id")
    private String tokenId;
    @Column(columnDefinition = "varchar(4096) comment 'token' ", nullable = false, name = "token")
    private String token;
    @Column(columnDefinition = "varchar(4096) comment 'authentication' ", nullable = false, name = "authentication")
    private String authentication;

    public Long getIotOauthRefreshTokenId() {
        return iotOauthRefreshTokenId;
    }

    public void setIotOauthRefreshTokenId(Long iotOauthRefreshTokenId) {
        this.iotOauthRefreshTokenId = iotOauthRefreshTokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
