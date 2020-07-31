package allthings.iot.dos.dto;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 15:55
 * @description:
 **/
public class IotUseLogDTO extends AbstractIotDosDTO {
    private Long iotUseLogId;

    private String userIp;

    private String clientId;

    private String path;

    private String param;

    public Long getIotUseLogId() {
        return iotUseLogId;
    }

    public void setIotUseLogId(Long iotUseLogId) {
        this.iotUseLogId = iotUseLogId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
