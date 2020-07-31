package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-07 18:01
 */

public class IotDeviceTypeDeleteDTO extends AbstractIotDosDTO {
    /**
     * 设备类型ID
     */
    Long[] iotDeviceTypeIds;

    /**
     * 项目ID
     */
    Long iotProjectId;

    /**
     * 设备类型编码
     */
    private List<String> devieTypeCodes;

    public Long[] getIotDeviceTypeIds() {
        return iotDeviceTypeIds;
    }

    public void setIotDeviceTypeIds(Long[] iotDeviceTypeIds) {
        this.iotDeviceTypeIds = iotDeviceTypeIds;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public List<String> getDevieTypeCodes() {
        return devieTypeCodes;
    }

    public void setDevieTypeCodes(List<String> devieTypeCodes) {
        this.devieTypeCodes = devieTypeCodes;
    }
}
