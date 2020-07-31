package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  GpsQueryDto
 * @CreateDate :  2018/1/16
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
public class GpsQueryDto implements Serializable {
    /**
     * 大客户GPS厂商轨迹上传专用接口partyid约定值8888，业务使用方无需感知;默认-100
     */
    private int partyid;
    /**
     * 设备号标识最长64位
     */
    private String deviceid;
    /**
     * 查询的开始时间
     */
    private String starttime;
    /**
     * 查询的结束时间
     */
    private String endtime;
    /**
     * 每页条数
     */
    private Integer skipCount;

    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 坐标系  WGS84  BD09 GCJ02 不传入 默认为 WGS84
     */
    private String format;

    /**
     * 业务编码
     */
    private String businessCode;

    /**
     * 时间
     */
    private Long dateTime;

    private Long startTime;
    /**
     * 查询的结束时间
     */
    private Long endTime;

    public int getPartyid() {
        return partyid;
    }

    public void setPartyid(int partyid) {
        this.partyid = partyid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "GpsQueryDto{" +
                "partyid=" + partyid +
                ", deviceid='" + deviceid + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", skipCount=" + skipCount +
                ", pageSize=" + pageSize +
                ", format='" + format + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", dateTime=" + dateTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
