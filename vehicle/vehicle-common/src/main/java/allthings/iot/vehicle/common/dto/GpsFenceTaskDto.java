package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  GpsFenceTaskDto
 * @CreateDate :  2018/1/16
 * @Description : 围栏任务dto
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class GpsFenceTaskDto implements Serializable {
    /**
     * 围栏编码 entityid fencetype字段自定义围栏类型下，该字段必须指定
     */
    private String entityId;
    /**
     * 大客户GPS厂商轨迹上传专用接口partyid约定值8888，业务使用方无需感知;默认-100
     */
    private String partyId;
    /**
     * 设备编码
     */
    private String deviceId;
    /**
     * fencetype 围栏的类型，自定义类型下必须指定entityid字段
     * <p>
     * 自定义/点围栏（圆/方）
     */
    private String fenceType;
    /**
     * 业务号
     */
    private String businessNumber;
    /**
     * 附加参数
     */
    private String callbackParams;
    /**
     * 回调地址
     */
    private String callbackUrl;
    /**
     * 触发类型说明：1进入围栏时触发，2出去围栏时触发，3进入出去均触发
     */
    private Integer triggerType;
    /**
     * 触发次数说明：触发次数，如果进出都需要触发，建议偶数倍数，值-1代表不限次数
     */
    private Integer triggerCount;
    /**
     * 生效时间
     */
    private String validDate;
    /**
     * 失效时间
     */
    private String invalidDate;
    /**
     * 任务描述
     */
    private String description;
    /**
     * app名称
     */
    private String appId;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFenceType() {
        return fenceType;
    }

    public void setFenceType(String fenceType) {
        this.fenceType = fenceType;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getCallbackParams() {
        return callbackParams;
    }

    public void setCallbackParams(String callbackParams) {
        this.callbackParams = callbackParams;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getTriggerCount() {
        return triggerCount;
    }

    public void setTriggerCount(Integer triggerCount) {
        this.triggerCount = triggerCount;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
