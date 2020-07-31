package allthings.iot.ktv.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  OilQueryDto
 * @CreateDate :  2018/1/18
 * @Description : 油耗查询dto
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class OilQueryDto implements Serializable {
    /**
     * 会员id
     */
    private String partyId;
    /**
     * 设备标识
     */
    private String deviceId;
    /**
     * 开始时间
     */
    private Long startDatetime;
    /**
     * 结束时间
     */
    private Long endDatetime;
    /**
     * 油箱容量
     */
    private int tankCapacity = 200;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public int getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(int tankCapacity) {
        this.tankCapacity = tankCapacity;
    }
}
