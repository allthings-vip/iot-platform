package allthings.iot.dos.dto;

/**
 * @author tyf
 * @date 2020/08/03 14:43:43
 */
public class IotOauthAccessTokenDto extends AbstractIotDosDTO {
    private Long iotOauthAccessTokenId;
    private String tokenId;
    private String token;
    private String authentication;
    private String authenticationId;
    private String username;
    private String clientId;
    private String refreshToken;

    public Long getIotOauthAccessTokenId() {
        return iotOauthAccessTokenId;
    }

    public void setIotOauthAccessTokenId(Long iotOauthAccessTokenId) {
        this.iotOauthAccessTokenId = iotOauthAccessTokenId;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
