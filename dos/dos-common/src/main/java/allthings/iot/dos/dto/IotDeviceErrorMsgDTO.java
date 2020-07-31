package allthings.iot.dos.dto;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceErrorMsgDTO
 * @CreateDate :  2018-5-11
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
public class IotDeviceErrorMsgDTO implements Serializable {
    /**
     * 行数
     */
    private Integer rowNum;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 错误信息列表
     */
    private List<String> errorMsgList = Lists.newArrayList();

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public List<String> getErrorMsgList() {
        return errorMsgList;
    }

    public void setErrorMsgList(List<String> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }
}
