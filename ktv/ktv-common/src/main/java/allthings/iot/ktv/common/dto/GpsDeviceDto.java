package allthings.iot.ktv.common.dto;

import java.io.Serializable;
import java.util.List;

public class GpsDeviceDto  implements Serializable {

    /**
     * 设备编码
     */
    private String deviceId;
    /**
     * 位置信息
     */
    private GpsDto gps;
    /**
     * 因子键值对数组
     */
    private List<KvsDto> kvs;

    private String parkingTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public GpsDto getGps() {
        return gps;
    }

    public void setGps(GpsDto gps) {
        this.gps = gps;
    }

    public List<KvsDto> getKvs() {
        return kvs;
    }

    public void setKvs(List<KvsDto> kvs) {
        this.kvs = kvs;
    }

    public String getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
    }
}
