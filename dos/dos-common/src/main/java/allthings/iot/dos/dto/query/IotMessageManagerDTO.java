package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-09 14:02
 */

public class IotMessageManagerDTO implements Serializable {
    /**
     * 申请的消息编号
     */
    private String messageCenterId;

    /**
     * 有效时间
     */
    private String minute;

    /**
     * 手机号码
     */
    private String mobileNumber;

    /**
     * 签名
     */
    private String sign;

    /**
     * 验证码
     */
    private String code;

    public String getMessageCenterId() {
        return messageCenterId;
    }

    public void setMessageCenterId(String messageCenterId) {
        this.messageCenterId = messageCenterId;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
