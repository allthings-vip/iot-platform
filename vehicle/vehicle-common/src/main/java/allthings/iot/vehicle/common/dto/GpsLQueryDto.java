package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  GpsLQueryDto
 * @CreateDate :  2018/1/17
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
public class GpsLQueryDto implements Serializable {

    /**
     * 设备标识列表（逗号分割）
     */
    private String deviceIds;
    /**
     * 坐标标注
     */
    private String format;

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
