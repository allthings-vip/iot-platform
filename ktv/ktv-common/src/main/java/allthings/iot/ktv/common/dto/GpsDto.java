package allthings.iot.ktv.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  GpsDto
 * @CreateDate :  2018/1/15
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
public class GpsDto implements Serializable {
    /**
     * 会员id
     */
    private String partyId;
    /**
     * 地址描述
     */
    private String addressDesc;
    /**
     * 坐标系
     */
    private String format;
    /**
     * 海拨
     */
    private String gpsAltitude;
    /**
     * 方向
     */
    private String gpsDirection;
    /**
     * 纬度
     */
    private String gpsLatitude;
    /**
     * 经度
     */
    private String gpsLongitude;
    /**
     * 速度
     */
    private String gpsSpeed;
    /**
     * 时间
     */
    private String gpsTime;
    /**
     * 定位模式
     */
    private String mode;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGpsAltitude() {
        return gpsAltitude;
    }

    public void setGpsAltitude(String gpsAltitude) {
        this.gpsAltitude = gpsAltitude;
    }

    public String getGpsDirection() {
        return gpsDirection;
    }

    public void setGpsDirection(String gpsDirection) {
        this.gpsDirection = gpsDirection;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(String gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
