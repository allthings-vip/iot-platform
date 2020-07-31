package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2018/09/05 14:15
 */
public class SaveKtvFactorDto implements Serializable {

    /**
     * 设备编码
     */
    private String deviceId;

    /**
     * 因子编码
     */
    private String code;

    /**
     * 因子值
     */
    private String value;

    /**
     * 采集时间
     */
    private Long realtime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getRealtime() {
        return realtime;
    }

    public void setRealtime(Long realtime) {
        this.realtime = realtime;
    }
}
