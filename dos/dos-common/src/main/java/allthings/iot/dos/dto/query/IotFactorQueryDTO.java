package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotFactorQueryDTO
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
public class IotFactorQueryDTO implements Serializable {
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
     * 聚合类型ID
     */
    private Long iotDataAggTypeId;

    /**
     * 聚合类型名称
     */
    private String dataAggTypeName;

    /**
     * 聚合类型编码
     */
    private String dataAggTypeCode;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 单位符号
     */
    private String unitSymbol;

    public IotFactorQueryDTO() {
    }

    public IotFactorQueryDTO(Long iotFactorId, String factorCode, String factorName, Long iotDataAggTypeId,
                             String dataAggTypeName, String dataAggTypeCode, String unitName, String unitSymbol) {
        this(iotFactorId, factorCode, factorName, dataAggTypeName, dataAggTypeCode, unitName, unitSymbol);
        this.iotDataAggTypeId = iotDataAggTypeId;
    }

    public IotFactorQueryDTO(Long iotFactorId, String factorCode, String factorName, String dataAggTypeName,
                             String dataAggTypeCode, String unitName, String unitSymbol) {
        this.iotFactorId = iotFactorId;
        this.factorCode = factorCode;
        this.factorName = factorName;
        this.dataAggTypeName = dataAggTypeName;
        this.dataAggTypeCode = dataAggTypeCode;
        this.unitName = unitName;
        this.unitSymbol = unitSymbol;
    }

    public Long getIotDataAggTypeId() {
        return iotDataAggTypeId;
    }

    public void setIotDataAggTypeId(Long iotDataAggTypeId) {
        this.iotDataAggTypeId = iotDataAggTypeId;
    }

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

    public String getDataAggTypeName() {
        return dataAggTypeName;
    }

    public void setDataAggTypeName(String dataAggTypeName) {
        this.dataAggTypeName = dataAggTypeName;
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
