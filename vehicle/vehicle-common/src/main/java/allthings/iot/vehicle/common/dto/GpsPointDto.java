package allthings.iot.vehicle.common.dto;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  GpsPointDto
 * @CreateDate :  2018/1/16
 * @Description : 地图点位
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class GpsPointDto implements Serializable {
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
