package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotService
 * @CreateDate :  2019/5/9
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IotServiceDTO implements Serializable {

    private Long iotServiceId;

    private Long inputDate;

    private Long stampDate;

    private boolean isDeleted;

    private Long createOperatorId;

    private Long modifyOperatorId;

    private String serviceName;

    private String serviceCode;

    private String ip;

    private String port;

    private Integer levels;

    private String dependencyService;

    private Boolean status;

    public Long getIotServiceId() {
        return iotServiceId;
    }

    public void setIotServiceId(Long iotServiceId) {
        this.iotServiceId = iotServiceId;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }

    public Long getStampDate() {
        return stampDate;
    }

    public void setStampDate(Long stampDate) {
        this.stampDate = stampDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getCreateOperatorId() {
        return createOperatorId;
    }

    public void setCreateOperatorId(Long createOperatorId) {
        this.createOperatorId = createOperatorId;
    }

    public Long getModifyOperatorId() {
        return modifyOperatorId;
    }

    public void setModifyOperatorId(Long modifyOperatorId) {
        this.modifyOperatorId = modifyOperatorId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getDependencyService() {
        return dependencyService;
    }

    public void setDependencyService(String dependencyService) {
        this.dependencyService = dependencyService;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
