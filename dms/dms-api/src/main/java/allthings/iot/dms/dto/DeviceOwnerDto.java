package allthings.iot.dms.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  DeviceOwnerDto
 * @CreateDate :  2016/6/28
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) www.XXXXX.com   All Rights Reserved
 * *******************************************************************************************
 */
public class DeviceOwnerDto implements Serializable {

    private Long iotDeviceOwnerId;

    /**
     * 拥有者id
     */
    private String ownerId;

    /**
     * 设备Id
     */
    private String deviceId;

    /**
     * 绑定时间
     */
    private Date bindDatetime;

    /**
     * 是否已经绑定
     */
    private Boolean isBound;

    /**
     * 解除绑定时间
     */
    private Date unBindDatetime;

    private Date inputDate;

    private Date updateDate;

    private Date stampDate;

    private boolean isDelete;

    public Long getIotDeviceOwnerId() {
        return iotDeviceOwnerId;
    }

    public void setIotDeviceOwnerId(Long iotDeviceOwnerId) {
        this.iotDeviceOwnerId = iotDeviceOwnerId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getBindDatetime() {
        return bindDatetime;
    }

    public void setBindDatetime(Date bindDatetime) {
        this.bindDatetime = bindDatetime;
    }

    public Boolean getBound() {
        return isBound;
    }

    public void setBound(Boolean bound) {
        isBound = bound;
    }

    public Date getUnBindDatetime() {
        return unBindDatetime;
    }

    public void setUnBindDatetime(Date unBindDatetime) {
        this.unBindDatetime = unBindDatetime;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
