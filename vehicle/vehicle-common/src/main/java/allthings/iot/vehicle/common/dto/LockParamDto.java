package allthings.iot.vehicle.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2020/06/09 16:42:46
 */
public class LockParamDto implements Serializable {
    /**
     * 设备号列表
     */
    private List<String> deviceCodes;

    /**
     * 厂商编码
     * 0-吉利，1-车贷管家
     */
    private Integer type;

    public List<String> getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(List<String> deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LockParamDto{" +
                "deviceCodes=" + deviceCodes +
                ", type=" + type +
                '}';
    }
}
