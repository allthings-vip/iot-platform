package allthings.iot.dms.entity;


import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author :  sylar
 * @FileName :  IotDeviceInfo
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
@Table(appliesTo = "IotDeviceInfo", comment = "设备信息表")
public class IotDeviceInfo extends AbstractDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceInfoId'")
    private Long iotDeviceInfoId;

    @Column(nullable = false, columnDefinition = " int(10) comment '版本编号' ")
    private int versionCode;

    @Column(columnDefinition = " varchar(255) comment '终端物理地址' ")
    private String mac;

    @Column(columnDefinition = " varchar(255) comment 'bid' ")
    private String bid;

    @Column(columnDefinition = " char(1) comment '是否可用' ")
    @Type(type = "yes_no")
    private boolean enable = true;

    public Long getIotDeviceInfoId() {
        return iotDeviceInfoId;
    }

    public void setIotDeviceInfoId(Long iotDeviceInfoId) {
        this.iotDeviceInfoId = iotDeviceInfoId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
