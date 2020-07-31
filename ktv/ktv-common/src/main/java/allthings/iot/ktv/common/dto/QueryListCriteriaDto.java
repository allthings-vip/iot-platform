package allthings.iot.ktv.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2018/09/03 10:16
 */
public class QueryListCriteriaDto implements Serializable {

    /**
     * 比较条件
     */
    private List<ComparisonCriteriaDto> conditions;

    /**
     * 设备编码id
     */
    private List<String> deviceIds;

    /**
     * 因子列表
     */
    private String[] factors;

    /**
     * 会员id
     */
    private Integer partyId;

    public List<ComparisonCriteriaDto> getConditions() {
        return conditions;
    }

    public void setConditions(List<ComparisonCriteriaDto> conditions) {
        this.conditions = conditions;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String[] getFactors() {
        return factors;
    }

    public void setFactors(String[] factors) {
        this.factors = factors;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }
}
