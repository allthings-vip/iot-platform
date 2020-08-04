package allthings.iot.dos.dto.oauth2;

import allthings.iot.dos.dto.AbstractIotDosDTO;

public class OauthCodeDTO extends AbstractIotDosDTO {
    private String authentication;

    private String code;

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
