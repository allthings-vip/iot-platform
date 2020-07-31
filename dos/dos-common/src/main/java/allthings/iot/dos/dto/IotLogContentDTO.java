package allthings.iot.dos.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author :  txw
 * @FileName :  IotLogContentDTO
 * @CreateDate :  2018/11/27
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IotLogContentDTO implements Serializable {


    /**
     * connected : true
     * dasNodeId : 00-50-56-87-00-00_25211
     * msgCode : 0010
     * msgId : G7_浙A5T516_0010_1543221458049
     * occurTime : 1543221458049
     * params : {"ex_content":1}
     * sourceDeviceId : 浙A5T516
     * sourceDeviceType : G7
     * targetDeviceType : CLOUD
     */

    private Boolean connected;
    private String dasNodeId;
    private String msgCode;
    private String msgId;
    private Long occurTime;
    private Map<String, Object> params;
    private String sourceDeviceId;
    private String sourceDeviceType;
    private String targetDeviceType;

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getDasNodeId() {
        return dasNodeId;
    }

    public void setDasNodeId(String dasNodeId) {
        this.dasNodeId = dasNodeId;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Long getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Long occurTime) {
        this.occurTime = occurTime;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getSourceDeviceId() {
        return sourceDeviceId;
    }

    public void setSourceDeviceId(String sourceDeviceId) {
        this.sourceDeviceId = sourceDeviceId;
    }

    public String getSourceDeviceType() {
        return sourceDeviceType;
    }

    public void setSourceDeviceType(String sourceDeviceType) {
        this.sourceDeviceType = sourceDeviceType;
    }

    public String getTargetDeviceType() {
        return targetDeviceType;
    }

    public void setTargetDeviceType(String targetDeviceType) {
        this.targetDeviceType = targetDeviceType;
    }
}
