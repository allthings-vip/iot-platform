package allthings.iot.ktv.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2018/10/06 15:37
 */
public class BatchQueryDto implements Serializable {
    /**
     * 会员id
     */
    private String partyId;
    /**
     * 设备标识列表
     */
    private List<String> deviceIds;
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

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
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
}
