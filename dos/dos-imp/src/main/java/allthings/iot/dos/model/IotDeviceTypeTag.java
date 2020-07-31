package allthings.iot.dos.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeTag
 * @CreateDate :  2018/4/29
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
@Entity
@Table(name = "iot_device_type_tag")
public class IotDeviceTypeTag extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '设备类型标签唯一编码' ", name = "iot_dos_devicetype_tag_id")
    private Long iotDeviceTypeTagId;

    @Column(columnDefinition = " int(20) comment '设备类型唯一编码' ", name = "iot_dos_devicetype_id", nullable = false)
    private Long iotDeviceTypeId;

    @Column(columnDefinition = " int(20) comment '标签唯一编码' ", name = "iot_dos_tag_id", nullable = false)
    private Long iotTagId;

    public Long getIotDeviceTypeTagId() {
        return iotDeviceTypeTagId;
    }

    public void setIotDeviceTypeTagId(Long iotDeviceTypeTagId) {
        this.iotDeviceTypeTagId = iotDeviceTypeTagId;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public Long getIotTagId() {
        return iotTagId;
    }

    public void setIotTagId(Long iotTagId) {
        this.iotTagId = iotTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof IotDeviceTypeTag)) {
            return false;
        }

        IotDeviceTypeTag that = (IotDeviceTypeTag) o;
        return Objects.equals(iotDeviceTypeId, that.iotDeviceTypeId) &&
                Objects.equals(iotTagId, that.iotTagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iotDeviceTypeId, iotTagId);
    }
}
