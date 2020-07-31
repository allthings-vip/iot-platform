package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author :  luhao
 * @FileName :  IotProjectDeviceType
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
//@Table(name = "iot_dos_project_device_type")
public class IotProjectDeviceType extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '项目设备类型唯一编码' ", name = "iot_dos_project_device_type_id")
    private Long iotProjectDeviceTypeId;

    @Column(columnDefinition = " int(20) comment '设备类型唯一编码' ", name = "iot_dos_device_type_id", nullable = false)
    private Long iotDeviceTypeId;

    @Column(columnDefinition = " int(20) comment '项目唯一编码' ", name = "iot_dos_project_id", nullable = false)
    private Long iotProjectId;

    public Long getIotProjectDeviceTypeId() {
        return iotProjectDeviceTypeId;
    }

    public void setIotProjectDeviceTypeId(Long iotProjectDeviceTypeId) {
        this.iotProjectDeviceTypeId = iotProjectDeviceTypeId;
    }

    public Long getIotDeviceTypeId() {
        return iotDeviceTypeId;
    }

    public void setIotDeviceTypeId(Long iotDeviceTypeId) {
        this.iotDeviceTypeId = iotDeviceTypeId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IotProjectDeviceType)) {
            return false;
        }
        IotProjectDeviceType that = (IotProjectDeviceType) o;
        return Objects.equals(iotDeviceTypeId, that.iotDeviceTypeId) &&
                Objects.equals(iotProjectId, that.iotProjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iotDeviceTypeId, iotProjectId);
    }
}
