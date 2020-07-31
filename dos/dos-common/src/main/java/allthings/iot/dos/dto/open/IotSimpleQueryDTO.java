package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotSimpleQueryDTO
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
public class IotSimpleQueryDTO implements Serializable {

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 结束时间
     */
    private Long endDatetime;

    /**
     * 开始时间
     */
    private Long startDatetime;

    /**
     * 油箱容量
     */
    private Integer tankCapacity;

    private String clientId;

    public IotSimpleQueryDTO() {
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Integer getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(Integer tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
