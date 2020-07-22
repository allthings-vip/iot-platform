package tf56.cloud.iot.store.dustbin.data.dto;

/**
 * @author :  luhao
 * @FileName :  DustbinDeviceDataDto
 * @CreateDate :  2017/11/17
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class DustbinDeviceDataDto extends BaseDto {
    private Long dustbinDeviceDataId;
    /**
     * 因子代码，对应DeviceFactorModel的factorCode
     */

    private String factorCode;

    /**
     * 采集的原始值
     */

    private Double originalValue;

    /**
     * 修正值
     */
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
