package allthings.iot.dos.dto.open;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotVehicleLastestBatchDTO
 * @CreateDate :  2018/11/13
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IotVehicleLastestBatchDTO implements Serializable {

    /**
     * 查询条件
     */
    private List<IotQueryConditionDTO> conditions;

    /**
     * 设备编码列表
     */
    private List<String> deviceCodes;

    /**
     * 因子列表
     */
    private String[] factors;

    private String clientId;

    public IotVehicleLastestBatchDTO() {
    }

    public List<IotQueryConditionDTO> getConditions() {
        return conditions;
    }

    public void setConditions(List<IotQueryConditionDTO> conditions) {
        this.conditions = conditions;
    }

    public List<String> getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(List<String> deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public String[] getFactors() {
        return factors;
    }

    public void setFactors(String[] factors) {
        this.factors = factors;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
