package allthings.iot.dos.model.offline;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotDeviceCount
 * @CreateDate :  2018-5-16
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
@Table(name = "iot_dos_device_count")
public class IotDeviceCount extends AbstractIotDosOfflineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(20) comment '设备数量自增主键' ", name = "iot_dos_device_count_id")
    private Long iotDeviceCountId;

    @Column(columnDefinition = " int(20) comment '设备类型唯一编码' ", nullable = false, name = "iot_dos_devicetype_id")
    private Long iotDeviceTypeId;

    @Column(columnDefinition = " int(20) comment '项目唯一编码' ", nullable = false, name = "iot_dos_project_id")
    private Long iotProjectId;

    @Column(columnDefinition = "int(20) comment '当天数量' ", nullable = false, name = "day_quantity")
    private Long dayQuantity;

    @Column(columnDefinition = "int(20) comment '当天之前总数量' ", nullable = false, name = "day_before_quantity")
    private Long dayBeforeQuantity;

    public Long getIotDeviceCountId() {
        return iotDeviceCountId;
    }

    public void setIotDeviceCountId(Long iotDeviceCountId) {
        this.iotDeviceCountId = iotDeviceCountId;
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

    public Long getDayQuantity() {
        return dayQuantity;
    }

    public void setDayQuantity(Long dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public Long getDayBeforeQuantity() {
        return dayBeforeQuantity;
    }

    public void setDayBeforeQuantity(Long dayBeforeQuantity) {
        this.dayBeforeQuantity = dayBeforeQuantity;
    }
}
