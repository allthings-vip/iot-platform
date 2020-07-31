package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/03/15 12:37
 */
public class IotDevicePassListDto implements Serializable {

    private Long iotDevicePassId;
    private String passCode;
    private String passName;
    private List<Map> extendProperties;

    public Long getIotDevicePassId() {
        return iotDevicePassId;
    }

    public void setIotDevicePassId(Long iotDevicePassId) {
        this.iotDevicePassId = iotDevicePassId;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public List<Map> getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(List<Map> extendProperties) {
        this.extendProperties = extendProperties;
    }
}
