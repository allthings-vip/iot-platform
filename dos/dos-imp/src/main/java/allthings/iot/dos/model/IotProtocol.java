package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotProtocol
 * @CreateDate :  2018/4/25
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(name = "iot_dos_protocol")
public class IotProtocol extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotProtocolId' ", name = "iot_dos_protocol_id")
    private Long iotProtocolId;

    @Column(columnDefinition = " varchar(500) comment '协议名称' ", name = "protocol_name", nullable = false)
    private String protocolName;

    @Column(columnDefinition = " varchar(500) comment '协议编码' ", name = "protocol_code", nullable = false)
    private String protocolCode;

    @Column(columnDefinition = " text comment '简介' ", name = "description")
    private String description;

    @Column(columnDefinition = " varchar(500)  comment '服务器域名' ", name = "server_domain", nullable = false)
    private String serverDomain;

    @Column(columnDefinition = " varchar(500)  comment '服务器ip' ", name = "server_ip", nullable = false)
    private String serverIp;

    @Column(columnDefinition = " int comment '服务器端口' ", name = "server_port", nullable = false)
    private Integer serverPort;

    @Column(columnDefinition = " varchar(500)  comment '测试服务器域名' ", name = "test_server_domain", nullable = false)
    private String testServerDomain;

    @Column(columnDefinition = " varchar(500)  comment '测试服务器ip' ", name = "test_server_ip", nullable = false)
    private String testServerIp;

    @Column(columnDefinition = " int comment '测试服务器端口' ", name = "test_server_port", nullable = false)
    private Integer testServerPort;

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public Long getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(Long iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getTestServerDomain() {
        return testServerDomain;
    }

    public void setTestServerDomain(String testServerDomain) {
        this.testServerDomain = testServerDomain;
    }

    public String getTestServerIp() {
        return testServerIp;
    }

    public void setTestServerIp(String testServerIp) {
        this.testServerIp = testServerIp;
    }

    public Integer getTestServerPort() {
        return testServerPort;
    }

    public void setTestServerPort(Integer testServerPort) {
        this.testServerPort = testServerPort;
    }
}
