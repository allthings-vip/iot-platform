package allthings.iot.des.http;

/**
 * @author tyf
 * @date 2019/01/15 17:48
 */
public class RouterException extends RuntimeException {
    private int code;

    public RouterException() {
        super();
    }

    public RouterException(int Code, String message) {
        super(message);
        code = Code;
    }

    public RouterException(String message, Throwable cause,
                           boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RouterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouterException(String message) {
        super(message);
    }

    public RouterException(Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
