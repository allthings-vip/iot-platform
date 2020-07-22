package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author :  sylar
 * @FileName :  IotDeviceLocation
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(appliesTo = "IotDeviceLocation", comment = "设备位置表")
public class IotDeviceLocation extends AbstractDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceLocationId' ")
    private Long iotDeviceLocationId;

    @Column(columnDefinition = " varchar(255) comment '用户编码' ")
    private String userId;

    @Column(columnDefinition = " varchar(255) comment '位置描述' ")
    private String locationDesc;

    /**
     * wgs84 国际坐标, 形如"x,y"
     */
    @Column(columnDefinition = " varchar(255) comment '国际坐标' ")
    private String wgsCoor;

    /**
     * gcj02 国测局加密坐标, 形如"x,y"
     */
    @Column(columnDefinition = " varchar(255) comment '国测局加密坐标' ")
    private String gcjCoor;


    /**
     * bd09ll 百度加密坐标, 形如"x,y"
     */
    @Column(columnDefinition = " varchar(255) comment '百度加密坐标' ")
    private String bdCoor;

    public Long getIotDeviceLocationId() {
        return iotDeviceLocationId;
    }

    public void setIotDeviceLocationId(Long iotDeviceLocationId) {
        this.iotDeviceLocationId = iotDeviceLocationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getWgsCoor() {
        return wgsCoor;
    }

    public void setWgsCoor(String wgsCoor) {
        this.wgsCoor = wgsCoor;
    }

    public String getGcjCoor() {
        return gcjCoor;
    }

    public void setGcjCoor(String gcjCoor) {
        this.gcjCoor = gcjCoor;
    }

    public String getBdCoor() {
        return bdCoor;
    }

    public void setBdCoor(String bdCoor) {
        this.bdCoor = bdCoor;
    }
}
