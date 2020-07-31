package allthings.iot.ktv.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  OilResultDto
 * @CreateDate :  2018/1/19
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
public class OilResultDto implements Serializable {
    /**
     * 设备标识
     */
    private String deviceId;
    /**
     * 油量
     */
    private double oil;
    /**
     * 里程数
     */
    private double gpsMileage;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getOil() {
        return oil;
    }

    public void setOil(double oil) {
        this.oil = oil;
    }

    public double getGpsMileage() {
        return gpsMileage;
    }

    public void setGpsMileage(double gpsMileage) {
        this.gpsMileage = gpsMileage;
    }
}
