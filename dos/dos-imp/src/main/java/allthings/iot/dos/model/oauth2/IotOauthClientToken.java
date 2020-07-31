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
 * @FileName :  IotOauthClientToken
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
@Table(name = "iot_oauth_client_token")
public class IotOauthClientToken extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iot_oauth_client_token_id' ", name = "iot_oauth_client_token_id")
    private Long iotOauthClientTokenId;
    @Column(columnDefinition = "varchar(500) comment 'token_id' ", nullable = false, name = "token_id")
    private String tokenId;
    @Column(columnDefinition = "varchar(4096) comment 'token' ", nullable = false, name = "token")
    private String token;
    @Column(columnDefinition = "varchar(500) comment 'authentication_id' ", nullable = false, name =
            "authentication_id")
    private String authenticationId;
    @Column(columnDefinition = "varchar(500) comment 'user_name' ", nullable = false, name = "user_name")
    private String username;
    @Column(columnDefinition = "varchar(500) comment 'client_id' ", nullable = false, name = "client_id")
    private String clientId;

    public Long getIotOauthClientTokenId() {
        return iotOauthClientTokenId;
    }

    public void setIotOauthClientTokenId(Long iotOauthClientTokenId) {
        this.iotOauthClientTokenId = iotOauthClientTokenId;
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

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
