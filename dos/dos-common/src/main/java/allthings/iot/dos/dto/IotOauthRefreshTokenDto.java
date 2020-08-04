package allthings.iot.dos.dto;

/**
 * @author tyf
 * @date 2020/08/03 14:49:26
 */
public class IotOauthRefreshTokenDto extends AbstractIotDosDTO {

    private Long iotOauthRefreshTokenId;
    private String tokenId;
    private String token;
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
