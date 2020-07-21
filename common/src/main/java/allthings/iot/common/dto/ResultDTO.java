package allthings.iot.common.dto;

import java.io.Serializable;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-06-26 16:50
 */

public class ResultDTO<T> implements Serializable {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = -1;
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

    /**
     * 判断是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == ResultDTO.CODE_SUCCESS;
    }

    /**
     * 创建成功对象
     *
     * @return
     */
    public static <T> ResultDTO<T> newSuccess() {
        return ResultDTO.newSuccess(null);
    }

    /**
     * @param data 数据对象
     * @return ResultDTO
     * @auther
     * @Date 2018/7/17
     * @Description 创建成功对象工具
     */
    public static <T> ResultDTO<T> newSuccess(T data) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(CODE_SUCCESS);
        resultDTO.setMsg(MSG_SUCCESS);
        resultDTO.setData(data);
        return resultDTO;
    }

    /**
     * @param data  数据对象
     * @param count 分页的页数
     * @return ResultDTO
     * @auther
     * @Date 2018/7/17
     * @Description 创建成功对象工具
     */
    public static <T> ResultDTO<T> newSuccess(T data, int count) {
        ResultDTO<T> resultDTO = ResultDTO.newSuccess(data);
        resultDTO.setCount(count);
        return resultDTO;
    }

    /**
     * @param data 数据对象
     * @param msg  成功时的提示消息
     * @return ResultDTO
     * @auther
     * @Date 2018/7/17
     * @Description 创建成功对象工具
     */
    public static <T> ResultDTO<T> newSuccess(T data, String msg) {
        ResultDTO<T> resultDTO = ResultDTO.newSuccess(data);
        resultDTO.setMsg(msg);
        return resultDTO;
    }

    /**
     * @param data 数据对象
     * @param code 成功状态码
     * @param msg  成功时的提示消息
     * @return ResultDTO
     * @auther
     * @Date 2018/7/17
     * @Description 创建成功对象工具
     */
    public static <T> ResultDTO<T> newSuccess(T data, int code, String msg) {
        ResultDTO<T> resultDTO = ResultDTO.newSuccess(data);
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);
        return resultDTO;
    }

    /**
     * @param data  数据对象
     * @param code  成功状态码
     * @param count 数量
     * @param msg   成功时的提示消息
     * @return ResultDTO
     * @auther
     * @Date 2018/7/17
     * @Description 创建成功对象工具
     */
    public static <T> ResultDTO<T> newSuccess(T data, int count, int code, String msg) {
        ResultDTO<T> resultDTO = ResultDTO.newSuccess(data);
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);
        resultDTO.setCount(count);
        return resultDTO;
    }

    /**
     * @param code 返回编码
     * @param msg  返回编码对应的解释
     * @return ResultDTO
     * @auther
     * @Date 2018/7/17
     * @Description 创建成功对象工具
     */
    public static <T> ResultDTO<T> newFail(int code, String msg) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);
        return resultDTO;
    }

    /**
     * 返回失败
     *
     * @param msg
     * @return
     */
    public static <T> ResultDTO<T> newFail(String msg) {
        return ResultDTO.newFail(CODE_FAIL, msg);
    }

    private ResultDTO() {

    }

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
        return "Result{" +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", count='" + count + '\'' +
                ", data=" + data +
                '}';
    }
}
