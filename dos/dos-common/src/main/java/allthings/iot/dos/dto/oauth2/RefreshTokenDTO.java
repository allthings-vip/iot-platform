package allthings.iot.dos.dto.oauth2;

public class RefreshTokenDTO extends AbstractDosOauth2DTO {
    private String tokenId;

    private String token;

    private String authentication;

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
