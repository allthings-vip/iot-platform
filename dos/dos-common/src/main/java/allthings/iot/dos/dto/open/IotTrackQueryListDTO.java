package allthings.iot.dos.dto.open;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotTrackQueryDTO
 * @CreateDate :  2018/11/12
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
public class IotTrackQueryListDTO implements Serializable {

    /**
     * 坐标系
     */
    private String coordinate;

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
     * 设备编码列表
     */
    private List<String> deviceCodes;

    private String clientId;

    public IotTrackQueryListDTO() {
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
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

    public List<String> getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(List<String> deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "IotTrackQueryListDTO{" +
                "coordinate='" + coordinate + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", startDatetime=" + startDatetime +
                ", endDatetime=" + endDatetime +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", deviceCodes=" + deviceCodes +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
