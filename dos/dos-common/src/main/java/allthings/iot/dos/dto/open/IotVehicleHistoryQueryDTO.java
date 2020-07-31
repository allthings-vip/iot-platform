package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotVehicleHistoryQueryDTO
 * @CreateDate :  2018/11/13
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
public class IotVehicleHistoryQueryDTO implements Serializable {

    /**
     * 设备标识
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
     * 因子列表
     */
    private String[] factors;

    /**
     * 坐标系
     */
    private String format;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 会员id
     */
    private String partyId;

    /**
     * 页码
     */
    private Integer skipCount;

    private Integer pageIndex;

    private String clientId;

    public IotVehicleHistoryQueryDTO() {
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

    public String[] getFactors() {
        return factors;
    }

    public void setFactors(String[] factors) {
        this.factors = factors;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
