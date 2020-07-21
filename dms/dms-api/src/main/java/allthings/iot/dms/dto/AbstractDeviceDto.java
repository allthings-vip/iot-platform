package allthings.iot.dms.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  AbstractDeviceDto
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
public abstract class AbstractDeviceDto implements Serializable {
    private String deviceType;

    private String deviceId;

    private Date inputDate;

    private Date updateDate;

    private Date stampDate;

    private String params;

    private boolean isDelete;

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

    @JSONField(serialize = false, deserialize = false)
    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
