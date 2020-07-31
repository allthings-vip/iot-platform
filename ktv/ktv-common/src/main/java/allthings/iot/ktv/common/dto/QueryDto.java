package allthings.iot.ktv.common.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author :  luhao
 * @FileName :  QueryDto
 * @CreateDate :  2018/3/28
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
public class QueryDto implements Serializable {
    /**
     * 会员id
     */
    private String partyId;
    /**
     * 设备标识
     */
    private String deviceId;
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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    @Override
    public String toString() {
        return "QueryDto{" +
                "partyId='" + partyId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", startDatetime=" + startDatetime +
                ", endDatetime=" + endDatetime +
                ", factors=" + Arrays.toString(factors) +
                '}';
    }
}
