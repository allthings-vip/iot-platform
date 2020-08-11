package allthings.iot.util.sms.dto;

import allthings.iot.util.sms.ErrorCodeEnum;

/**
 * @author tyf
 * @date 2020/08/07 15:05:40
 */
public class SendMsgResponseDto {
    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
     */
    private String bizId;

    /**
     * 请求状态码。
     * <p>
     * 返回OK代表请求成功。
     * 其他错误码详见错误码列表。
     * {@link ErrorCodeEnum}
     */
    private String code;

    /**
     * 状态码的描述。
     */
    private String message;

    /**
     * 请求ID。
     */
    private String requestId;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
