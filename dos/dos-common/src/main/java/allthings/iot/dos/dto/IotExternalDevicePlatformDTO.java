package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotExternalDevicePlatform
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
public class IotExternalDevicePlatformDTO implements Serializable {

    private Long iotExternalDevicePlatformId;

    private Long inputDate;

    private Long stampDate;

    private Boolean isDeleted;

    private Long createOperatorId;

    private Long modifyOperatorId;

    private String platformName;

    private String platformCode;

    private String dependencyService;

    private Boolean status;

    public Long getIotExternalDevicePlatformId() {
        return iotExternalDevicePlatformId;
    }

    public void setIotExternalDevicePlatformId(Long iotExternalDevicePlatformId) {
        this.iotExternalDevicePlatformId = iotExternalDevicePlatformId;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
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

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
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
