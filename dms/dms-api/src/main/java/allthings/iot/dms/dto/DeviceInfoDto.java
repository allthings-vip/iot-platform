package allthings.iot.dms.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author :  sylar
 * @FileName :  DeviceInfoDto
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
public class DeviceInfoDto extends AbstractDeviceDto {

    private Long iotDeviceInfoId;

    private int versionCode;

    private String mac;

    private String bid;

    private boolean enable = true;

    @JSONField(serialize = false, deserialize = false)
    public Long getIotDeviceInfoId() {
        return iotDeviceInfoId;
    }

    public void setIotDeviceInfoId(Long iotDeviceInfoId) {
        this.iotDeviceInfoId = iotDeviceInfoId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
