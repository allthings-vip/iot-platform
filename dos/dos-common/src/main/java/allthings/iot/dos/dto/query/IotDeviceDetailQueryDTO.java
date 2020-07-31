package allthings.iot.dos.dto.query;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceDetailQueryDTO
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
public class IotDeviceDetailQueryDTO extends IotDeviceQueryDTO {
    /**
     * 设备因子
     */
    private List<IotFactorQueryDTO> factorList;

    public List<IotFactorQueryDTO> getFactorList() {
        return factorList;
    }

    public void setFactorList(List<IotFactorQueryDTO> factorList) {
        this.factorList = factorList;
    }
}
