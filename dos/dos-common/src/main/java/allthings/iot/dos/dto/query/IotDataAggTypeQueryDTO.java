package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDataAggTypeQueryDTO
 * @CreateDate :  2018-5-10
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
public class IotDataAggTypeQueryDTO implements Serializable {
    /**
     * 聚合类型ID
     */
    private Long iotDataAggTypeId;
    /**
     * 聚合类型名称
     */
    private String dataAggTypeName;
    /**
     * 聚合类型代码
     */
    private String dataAggTypeCode;

    public IotDataAggTypeQueryDTO() {
    }

    public IotDataAggTypeQueryDTO(Long iotDataAggTypeId, String dataAggTypeName, String dataAggTypeCode) {
        this.iotDataAggTypeId = iotDataAggTypeId;
        this.dataAggTypeName = dataAggTypeName;
        this.dataAggTypeCode = dataAggTypeCode;
    }

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
