package tf56.cloud.iot.store.dustbin.data.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author :  sylar
 * @FileName :  BaseEntity
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Column(columnDefinition = " varchar(255) comment '创建人代码'")
    private String createUserId;


    @Column(columnDefinition = " varchar(255) comment '更新人代码'")
    private String updateUserId;

    @Column(columnDefinition = " datetime comment '数据创建时间'")
    private Date inputDate = new Date();

    @Column(columnDefinition = " datetime comment '记录修改时间' ")
    private Date updateDate = new Date();

    @Column(columnDefinition = " timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT " +
            "'大数据所需要的日期字段," +
            "记录更新时间' ")
    private Date stampDate;

    @Column(columnDefinition = " char(1) comment '是否逻辑删除'")
    @Type(type = "yes_no")
    private boolean isDelete;

    /**
     * 设备代码，对应每台设备的唯一编码
     */
    @Column(columnDefinition = " varchar(255) comment '设备编码'")
    private String deviceId;

    /**
     * 设备类型
     */
    @Column(columnDefinition = " varchar(20) comment '设备类型'")
    private String deviceType;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
