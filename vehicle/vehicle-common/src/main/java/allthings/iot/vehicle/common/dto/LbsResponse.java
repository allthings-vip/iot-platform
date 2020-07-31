package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  LbsResponse
 * @CreateDate :  2018/1/15
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class LbsResponse implements Serializable {

    public static final String SUCCESS = "success";

    public static final String ERROR = "error";

    /**
     * 响应结果 success  error
     */
    private String result;

    private Object data;
    /**
     * 记录条数
     */
    private int count;
    /**
     * 提示信息
     */
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
