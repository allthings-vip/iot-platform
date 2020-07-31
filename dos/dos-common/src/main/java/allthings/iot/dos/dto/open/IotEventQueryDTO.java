package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotEventQueryDTO
 * @CreateDate :  2018/11/14
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
public class IotEventQueryDTO implements Serializable {

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 开始时间
     */
    private Long startDatetime;

    /**
     * 结束时间
     */
    private Long endDatetime;

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 处理状态
     */
    private Boolean disposeStatus;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    public IotEventQueryDTO() {
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getDisposeStatus() {
        return disposeStatus;
    }

    public void setDisposeStatus(Boolean disposeStatus) {
        this.disposeStatus = disposeStatus;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

}
