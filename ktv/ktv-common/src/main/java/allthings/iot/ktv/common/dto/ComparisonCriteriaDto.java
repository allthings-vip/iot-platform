package allthings.iot.ktv.common.dto;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2018/09/03 10:12
 */
public class ComparisonCriteriaDto implements Serializable {

    /**
     * 因子
     */
    private String factor;

    /**
     * 比较符
     */
    private String comparison;

    /**
     * 值
     */
    private String value;

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
