package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotFactorValueQueryDTO
 * @CreateDate :  2018-5-19
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
public class IotFactorValueQueryDTO implements Serializable {
    /**
     * 因子代码
     */
    private String factorCode;
    /**
     * 时间戳
     */
    private Long inputDate;

    /**
     * 因子值
     */
    private Object value;

    /**
     * 单位符号
     */
    private String unit_symbol;

    /**
     * 因子名称
     */
    private String factorName;

    public String getFactorCode() {
        return factorCode;
    }

    public void setFactorCode(String factorCode) {
        this.factorCode = factorCode;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getUnit_symbol() {
        return unit_symbol;
    }

    public void setUnit_symbol(String unit_symbol) {
        this.unit_symbol = unit_symbol;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }
}
