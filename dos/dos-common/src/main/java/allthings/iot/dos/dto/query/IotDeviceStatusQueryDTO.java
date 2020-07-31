package allthings.iot.dos.dto.query;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tyf
 * @date 2018/07/22 14:39
 */
public class IotDeviceStatusQueryDTO implements Serializable {

    private Long iotDeviceId;

    private String deviceCode;

    private Integer connectStatus;

    private Long latestDisConnectDatetime;

    private String projectName;

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @JSONField(serialize = false)
    public boolean isConnected() {
        if (connectStatus == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setConnected(Boolean connected) {
        if (connected != null && connected) {
            connectStatus = 1;
        } else {
            connectStatus = 0;
        }
    }

    public Integer getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(Integer connectStatus) {
        this.connectStatus = connectStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getLatestDisConnectDatetime() {
        return latestDisConnectDatetime;
    }

    public void setLatestDisConnectDatetime(Date latestDisConnectDatetime) {
        if (latestDisConnectDatetime == null) {
            this.latestDisConnectDatetime = null;
        } else {
            this.latestDisConnectDatetime = latestDisConnectDatetime.getTime();
        }
    }

    @Override
    public String toString() {
        return "IotDeviceStatusQueryDTO{" +
                "iotDeviceId=" + iotDeviceId +
                ", deviceCode='" + deviceCode + '\'' +
                ", connectStatus='" + connectStatus + '\'' +
                ", latestDisConnectDatetime=" + latestDisConnectDatetime +
                '}';
    }
}
