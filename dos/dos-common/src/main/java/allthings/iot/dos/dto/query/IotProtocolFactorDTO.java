package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotProtocolFactorDTO
 * @CreateDate :  2018/5/3
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
public class IotProtocolFactorDTO implements Serializable {
    /**
     * 因子唯一标识
     */
    private Long iotFactorId;
    /**
     * 因子名称
     */
    private String factorName;

    public IotProtocolFactorDTO(Long iotFactorId, String factorName) {
        this.iotFactorId = iotFactorId;
        this.factorName = factorName;
    }

    public Long getIotFactorId() {
        return iotFactorId;
    }

    public void setIotFactorId(Long iotFactorId) {
        this.iotFactorId = iotFactorId;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }
}
