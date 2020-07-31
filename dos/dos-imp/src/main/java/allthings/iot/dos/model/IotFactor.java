package allthings.iot.dos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotFactor
 * @CreateDate :  2018/4/26
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
@Entity
@Table(name = "iot_dos_factor")
public class IotFactor extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '因子唯一编码' ", name = "iot_dos_factor_id")
    private Long iotFactorId;

    @Column(columnDefinition = " varchar(500) comment '因子代码' ", name = "factor_code", nullable = false)
    private String factorCode;

    @Column(columnDefinition = " varchar(500) comment '因子名称' ", name = "factor_name", nullable = false)
    private String factorName;

    @Column(columnDefinition = " int comment '聚合类型' ", name = "iot_dos_data_agg_type_id", nullable = false)
    private int iotDataAggTypeId;

    @Column(columnDefinition = " varchar(500) comment '单位名称' ", name = "unit_name", nullable = false)
    private String unitName;

    @Column(columnDefinition = " varchar(500) comment '单位符号' ", name = "unit_symbol", nullable = false)
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

    public int getIotDataAggTypeId() {
        return iotDataAggTypeId;
    }

    public void setIotDataAggTypeId(int iotDataAggTypeId) {
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
