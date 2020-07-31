package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotDeviceCountQueryDTO
 * @CreateDate :  2018-5-8
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
public class IotDeviceCountQueryDTO implements Serializable {
    /**
     * 设备数量
     */
    private Long deviceCounts;
    /**
     * 时间
     */
    private Long inputDate;

    public IotDeviceCountQueryDTO() {
    }

    public IotDeviceCountQueryDTO(Long deviceCounts, Date inputDate) {
        this.deviceCounts = deviceCounts;
        this.inputDate = inputDate.getTime();
    }

    public Long getDeviceCounts() {
        return deviceCounts;
    }

    public void setDeviceCounts(Long deviceCounts) {
        this.deviceCounts = deviceCounts;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }
}
