package allthings.iot.dos.dto;

/**
 * @author :  luhao
 * @FileName :  IotProtocolDTO
 * @CreateDate :  2018-6-2
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
public class IotProtocolDTO extends AbstractIotDosDTO {
    /**
     * 自增主键
     */
    private Long iotProtocolId;

    /**
     * 协议名称
     */
    private String protocolName;

    /**
     * 协议编码
     */
    private String protocolCode;

    /**
     * 简介
     */
    private String description;

    /**
     * 服务器域名
     */
    private String serverDomain;

    /**
     * 服务器ip
     */
    private String serverIp;

    /**
     * 服务器端口
     */
    private Integer serverPort;

    /**
     * 测试服务器域名
     */
    private String testServerDomain;

    /**
     * 测试服务器ip
     */
    private String testServerIp;

    /**
     * 测试服务器端口
     */
    private Integer testServerPort;

    /**
     * 因子id数组
     */
    private Long[] iotFactorIds;

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

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
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

    public Long[] getIotFactorIds() {
        return iotFactorIds;
    }

    public void setIotFactorIds(Long[] iotFactorIds) {
        this.iotFactorIds = iotFactorIds;
    }
}
