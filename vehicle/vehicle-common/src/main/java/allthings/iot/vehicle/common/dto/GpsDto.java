package allthings.iot.vehicle.common.dto;

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
     * 行驶速度(单位km/h)
     */
    private String speed;
    /**
     * 大客户GPS厂商轨迹上传专用接口partyid约定值8888，业务使用方无需感知;默认-100
     */
    private String partyid;
    /**
     * 设备号标识最长64位
     */
    private String deviceId;
    /**
     * 经度(longitude)
     */
    private String longitude;
    /**
     * 纬度(latitude)
     */
    private String latitude;
    /**
     * 实时时间
     */
    private String realtime;

    /**
     * 行驶方向(如目前无法提供可不传)
     */
    private String direction;

    /**
     * 坐标系
     */
    private String coordinates;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPartyid() {
        return partyid;
    }

    public void setPartyid(String partyid) {
        this.partyid = partyid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRealtime() {
        return realtime;
    }

    public void setRealtime(String realtime) {
        this.realtime = realtime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
