package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 09:51
 */

public class IotAppSecretQueryDTO extends AbstractIotDosDTO {
    /**
     * 项目唯一编码
     */
    private Long iotProjectId;

    /**
     * AppSecret
     */
    private String iotAppSecret;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 手机号
     */
    private String mobile;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getIotAppSecret() {
        return iotAppSecret;
    }

    public void setIotAppSecret(String iotAppSecret) {
        this.iotAppSecret = iotAppSecret;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
