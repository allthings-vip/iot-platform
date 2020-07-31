package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @author tyf
 * @date 2019/03/08 16:37
 */
public class IotDeviceEventDetailQueryDto extends AbstractIotDosDTO {

    private Long iotProjectId;

    private Long iotDeviceId;

    private Long iotDesDeviceEventId;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public Long getIotDesDeviceEventId() {
        return iotDesDeviceEventId;
    }

    public void setIotDesDeviceEventId(Long iotDesDeviceEventId) {
        this.iotDesDeviceEventId = iotDesDeviceEventId;
    }
}
