package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2018/11/30 9:23
 */
public class ResultDto<T> implements Serializable {

    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "success";

    /**
     * 返回编码，0表示正确，其它表示错误
     */
    private int code;
    /**
     * 返回编码对应解释
     */
    private String msg;
    /**
     * 返回数据条数
     */
    private int count;
    /**
     * 返回的结果对象
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
