package allthings.iot.dos.dto.oauth2;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @author tyf
 * @date 2020/08/03 14:33:55
 */
public class IotOauthCodeDto extends AbstractIotDosDTO {

    private Long iotOauthCodeId;

    private String code;

    private String authentication;

    public Long getIotOauthCodeId() {
        return iotOauthCodeId;
    }

    public void setIotOauthCodeId(Long iotOauthCodeId) {
        this.iotOauthCodeId = iotOauthCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
