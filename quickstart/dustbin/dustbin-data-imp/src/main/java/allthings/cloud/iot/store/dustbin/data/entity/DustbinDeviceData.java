package tf56.cloud.iot.store.dustbin.data.entity;

import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author :  luhao
 * @FileName :  DustbinDeviceData
 * @CreateDate :  2017/11/20
 * @Description : 设备采集的数据，原始数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(appliesTo = "DustbinDeviceData", comment = "原始数据表")
public class DustbinDeviceData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = " int(20) comment 'dustbinDeviceDataId'")
    private Long dustbinDeviceDataId;



    /**
     * 因子代码，对应DeviceFactorModel的factorCode
     */
    @Column(columnDefinition = " varchar(255) comment '因子代码'")
    private String factorCode;

    /**
     * 采集的原始值
     */
    @Column(columnDefinition = " DECIMAL(20,5) comment '原始数值'")
    private Double originalValue;

    /**
     * 修正值
     */
    @Column(columnDefinition = " DECIMAL(20,5) comment '修正数值'")
    private Double correctValue;

    public Long getDustbinDeviceDataId() {
        return dustbinDeviceDataId;
    }

    public void setDustbinDeviceDataId(Long dustbinDeviceDataId) {
        this.dustbinDeviceDataId = dustbinDeviceDataId;
    }


    public String getFactorCode() {
        return factorCode;
    }

    public void setFactorCode(String factorCode) {
        this.factorCode = factorCode;
    }

    public Double getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Double originalValue) {
        this.originalValue = originalValue;
    }

    public Double getCorrectValue() {
        return correctValue;
    }

    public void setCorrectValue(Double correctValue) {
        this.correctValue = correctValue;
    }
}
