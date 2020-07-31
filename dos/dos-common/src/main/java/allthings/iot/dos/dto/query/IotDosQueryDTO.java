package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-07 17:28
 */

public class IotDosQueryDTO extends AbstractIotDosDTO {
    /**
     * 开始时间
     */
    private Long startDatetime;

    /**
     * 结束时间
     */
    private Long endDatetime;

    /**
     * 类型
     */
    private String type;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    public Long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
