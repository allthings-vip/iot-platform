package allthings.iot.dos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotDataAggType
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
@Table(name = "iot_dos_data_agg_type")
public class IotDataAggType extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '聚合类型唯一编码' ", name = "iot_dos_data_agg_type_id")
    private Long iotDataAggTypeId;

    @Column(columnDefinition = " varchar(500) comment '聚合类型名称' ", nullable = false, name = "data_agg_type_name")
    private String dataAggTypeName;

    @Column(columnDefinition = " varchar(500) comment '聚合类型代码' ", nullable = false, name = "data_agg_type_code")
    private String dataAggTypeCode;

    public Long getIotDataAggTypeId() {
        return iotDataAggTypeId;
    }

    public void setIotDataAggTypeId(Long iotDataAggTypeId) {
        this.iotDataAggTypeId = iotDataAggTypeId;
    }

    public String getDataAggTypeName() {
        return dataAggTypeName;
    }

    public void setDataAggTypeName(String dataAggTypeName) {
        this.dataAggTypeName = dataAggTypeName;
    }

    public String getDataAggTypeCode() {
        return dataAggTypeCode;
    }

    public void setDataAggTypeCode(String dataAggTypeCode) {
        this.dataAggTypeCode = dataAggTypeCode;
    }
}
