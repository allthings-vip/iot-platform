package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotGpsDTO
 * @CreateDate :  2018/11/12
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
public class IotGpsDTO implements Serializable {

    /**
     * 地址
     */
    private String address;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 定位模式 1:GPS|2:GSM|3:WIFI
     */
    private Integer mode;

    /**
     * 采集时间
     */
    private Long realtime;

    /**
     * 速度
     */
    private Double speed;

    public IotGpsDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Long getRealtime() {
        return realtime;
    }

    public void setRealtime(Long realtime) {
        this.realtime = realtime;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "IotGpsDTO{" +
                "address='" + address + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", mode=" + mode +
                ", realtime=" + realtime +
                ", speed=" + speed +
                '}';
    }
}
