package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-07 16:53
 */

public class IotProjectDeleteQueryDTO extends AbstractIotDosDTO {
    /**
     * 项目ID
     */
    private Long[] iotProjectIds;

    public Long[] getIotProjectIds() {
        return iotProjectIds;
    }

    public void setIotProjectIds(Long[] iotProjectIds) {
        this.iotProjectIds = iotProjectIds;
    }
}
