package allthings.iot.bsj.data.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  BsjCmdDto
 * @CreateDate :  2018/1/12
 * @Description : 指令dto
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class BsjCmdDto implements Serializable {
    /**
     * 指令
     */
    private String msgCode;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 参数
     */
    private Map<String, Object> params = new HashMap<>();

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
