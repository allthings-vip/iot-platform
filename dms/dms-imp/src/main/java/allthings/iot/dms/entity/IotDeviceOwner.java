package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotDeviceOwner
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
@Entity
@Table(appliesTo = "IotDeviceOwner", comment = "设备拥有者表")
public class IotDeviceOwner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceOwnerId' ")
    private Long iotDeviceOwnerId;

    /**
     * 拥有者id
     */
    @Column(nullable = false, columnDefinition = " varchar(255) comment '拥有者编码' ")
    private String ownerId;

    /**
     * 设备Id
     */
    @Column(nullable = false, columnDefinition = " varchar(255) comment '设备编码' ")
    private String deviceId;

    /**
     * 绑定时间
     */
    @Column(nullable = false, columnDefinition = " datetime comment '绑定时间' ")
    private Date bindDatetime;

    /**
     * 是否已经绑定
     */
    @Column(columnDefinition = " char(1) comment '是否已经绑定' ")
    @Type(type = "yes_no")
    private Boolean isBound;

    /**
     * 解除绑定时间
     */
    @Column(columnDefinition = " datetime comment '解除绑定时间' ")
    private Date unBindDatetime;

    @Column(columnDefinition = " datetime comment '数据创建时间'")
    private Date inputDate = new Date();

    @Column(columnDefinition = " datetime comment '记录修改时间' ")
    private Date updateDate = new Date();

    @Column(columnDefinition = " timestamp not null COMMENT '大数据所需要的日期字段,记录更新时间' ")
    private Date stampDate;

    @Column(columnDefinition = " char(1) comment '是否逻辑删除'")
    @Type(type = "yes_no")
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
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
}
