package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-21 13:12
 */

public class IotDeviceMessageDTO implements Serializable {
    /**
     * 设备Id
     */
    List<String> deviceIds;
    /**
     * 项目Id
     */
    Long iotProjectId;

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
