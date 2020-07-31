package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotOpenDeviceDTO
 * @CreateDate :  2018/12/11
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
public class IotOpenDeviceDTO implements Serializable {
    private String bizCode;
    private Integer connectStatus;
    private String deviceCode;
    private String deviceName;
    private Long inputDate;
    private Long latestConnectDatetime;
    private Long latestUploadDatetime;
    private String projectName;
    private Integer status;
    private String agencyName;
    private String newDeviceCode;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(Boolean connectStatus) {
        if (connectStatus == null || connectStatus == false) {
            this.connectStatus = 0;
        } else {
            this.connectStatus = 1;
        }
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }

    public Long getLatestConnectDatetime() {
        return latestConnectDatetime;
    }

    public void setLatestConnectDatetime(Long latestConnectDatetime) {
        this.latestConnectDatetime = latestConnectDatetime;
    }

    public Long getLatestUploadDatetime() {
        return latestUploadDatetime;
    }

    public void setLatestUploadDatetime(Long latestUploadDatetime) {
        this.latestUploadDatetime = latestUploadDatetime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status ? 1 : 0;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getNewDeviceCode() {
        return newDeviceCode;
    }

    public void setNewDeviceCode(String newDeviceCode) {
        this.newDeviceCode = newDeviceCode;
    }
}
