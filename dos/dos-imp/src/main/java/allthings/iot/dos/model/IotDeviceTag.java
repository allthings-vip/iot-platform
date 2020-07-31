package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  txw
 * @FileName :  IotDeviceTag
 * @CreateDate :  2018/10/30
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
@Entity
@Table(name = "iot_dos_device_tag")
public class IotDeviceTag extends AbstractIotDosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '自增ID' ", name = "iot_dos_device_tag_id")
    private Long iotDeviceTagId;

    @Column(columnDefinition = "int(20) comment '设备唯一编码' ", nullable = false, name = "iot_dos_device_id")
    private Long iotDeviceId;

    @Column(columnDefinition = "int(20) comment '标签唯一编码' ", nullable = false, name = "iot_dos_tag_id")
    private Long iotTagId;

    public Long getIotDeviceTagId() {
        return iotDeviceTagId;
    }

    public void setIotDeviceTagId(Long iotDeviceTagId) {
        this.iotDeviceTagId = iotDeviceTagId;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public Long getIotTagId() {
        return iotTagId;
    }

    public void setIotTagId(Long iotTagId) {
        this.iotTagId = iotTagId;
    }
}
