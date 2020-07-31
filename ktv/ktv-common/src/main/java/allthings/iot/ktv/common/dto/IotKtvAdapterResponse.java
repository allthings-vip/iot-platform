package allthings.iot.ktv.common.dto;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2018/10/09 9:02
 */
public class IotKtvAdapterResponse implements Serializable {

    public static final int SUCCESS = 0;

    /**
     * 响应结果 success  error
     */
    private String code;

    private Object data;
    /**
     * 记录条数
     */
    private String count;
    /**
     * 提示信息
     */
    private String msg;

    /**
     *
     */
    private String result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
