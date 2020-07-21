package allthings.iot.dms.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  AbstractDeviceEntity
 * @CreateDate :  2017/11/08
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
@MappedSuperclass
public abstract class AbstractDeviceEntity implements Serializable {
    @Column(nullable = false, columnDefinition = " varchar(30) comment '设备类型'")
    private String deviceType;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '设备编码'")
    private String deviceId;

    @Column(columnDefinition = " datetime comment '数据创建时间'")
    private Date inputDate = new Date();

    @Column(columnDefinition = " datetime comment '记录修改时间' ")
    private Date updateDate = new Date();

    @Column(columnDefinition = " timeStamp not null COMMENT '大数据所需要的日期字段,记录更新时间' ")
    private Date stampDate;

    @Column(columnDefinition = " char(1) comment '是否逻辑删除'")
    @Type(type = "yes_no")
    private boolean isDelete;
    /**
     * 自定义参数
     */
    @Column(name = "params", columnDefinition = "text comment '自定义参数'")
    @JSONField(serialize = false, deserialize = false)
    private String params;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    @JSONField(serialize = false, deserialize = false)
    public String getParams() {
        return params;
    }

    @JSONField(serialize = false, deserialize = false)
    public void setParams(String params) {
        this.params = params;
    }

    @JSONField(name = "params")
    public Map<String, Object> getMapParams() {

        try {
            if (Strings.isNullOrEmpty(params)) {
                return JSON.parseObject(this.params);
            } else {
                return Maps.newHashMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Maps.newHashMap();
        }
    }

    @JSONField(name = "params")
    public void setMapParams(Map<String, ?> mapParams) {
        try {
            this.params = JSON.toJSONString(mapParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
