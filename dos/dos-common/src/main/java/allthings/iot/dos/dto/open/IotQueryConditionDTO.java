package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotQueryConditionDTO
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
public class IotQueryConditionDTO implements Serializable {

    /**
     * 比较符
     */
    private String comparison;

    /**
     * 因子
     */
    private String factor;

    /**
     * 因子值
     */
    private String value;

    public IotQueryConditionDTO() {
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
