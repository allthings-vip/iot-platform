package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 15:48
 * @description:
 **/
@Entity
@Table(name = "iot_dos_use_log")
public class IotUseLog extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '接口使用记录Id' ", name = "iot_dos_use_log_id")
    private Long iotUseLogId;

    @Column(columnDefinition = " varchar(500) comment '调用方IP' ", nullable = false, name = "user_ip")
    private String userIp;

    @Column(columnDefinition = " varchar(500) comment '用户appkey' ", nullable = false, name = "client_id")
    private String clientId;

    @Column(columnDefinition = " varchar(500) comment '请求路径' ", nullable = false, name = "path")
    private String path;

    @Column(columnDefinition = " varchar(500) comment '请求参数' ", nullable = false, name = "param")
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
