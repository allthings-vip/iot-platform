package allthings.iot.dos.dto;

/**
 * @author :  luhao
 * @FileName :  IotFactorDTO
 * @CreateDate :  2018/5/7
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
public class IotFactorDTO extends AbstractIotDosDTO {
    /**
     * 因子唯一编码
     */
    private Long iotFactorId;

    /**
     * 因子代码
     */
    private String factorCode;

    /**
     * 因子名称
     */
    private String factorName;

    /**
     * 聚合类型
     */
    private Integer iotDataAggTypeId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 单位符号
     */
    private String unitSymbol;

    public Long getIotFactorId() {
        return iotFactorId;
    }

    public void setIotFactorId(Long iotFactorId) {
        this.iotFactorId = iotFactorId;
    }

    public String getFactorCode() {
        return factorCode;
    }

    public void setFactorCode(String factorCode) {
        this.factorCode = factorCode;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public Integer getIotDataAggTypeId() {
        return iotDataAggTypeId;
    }

    public void setIotDataAggTypeId(Integer iotDataAggTypeId) {
        this.iotDataAggTypeId = iotDataAggTypeId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }
}
