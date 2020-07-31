package allthings.iot.vehicle.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2018/09/05 14:12
 */
public class SaveDeviceDto implements Serializable {

    private String deviceId;

    private List<GpsDto> gpsList;

    private List<SaveKtvFactorDto> kvs;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<GpsDto> getGpsList() {
        return gpsList;
    }

    public void setGpsList(List<GpsDto> gpsList) {
        this.gpsList = gpsList;
    }

    public List<SaveKtvFactorDto> getKvs() {
        return kvs;
    }

    public void setKvs(List<SaveKtvFactorDto> kvs) {
        this.kvs = kvs;
    }
}
