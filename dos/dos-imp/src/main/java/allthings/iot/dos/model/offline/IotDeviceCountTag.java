package allthings.iot.dos.model.offline;


/**
 * @author :  luhao
 * @FileName :  IotDeviceCountTag
 * @CreateDate :  2018-5-17
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
@Table(name = "iot_dos_device_count_tag")
public class IotDeviceCountTag extends AbstractIotDosOfflineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(20) comment '设备数量标签分组自增主键' ", name = "iot_dos_device_count_tag_id")
    private Long iotDeviceCountTagId;

    @Column(columnDefinition = " int(20) comment '标签唯一编码' ", nullable = false, name = "iot_dos_tag_id")
    private Long iotTagId;

    @Column(columnDefinition = "int(20) comment '当天数量' ", nullable = false, name = "day_quantity")
    private Long dayQuantity;

    public Long getIotDeviceCountTagId() {
        return iotDeviceCountTagId;
    }

    public void setIotDeviceCountTagId(Long iotDeviceCountTagId) {
        this.iotDeviceCountTagId = iotDeviceCountTagId;
    }

    public Long getIotTagId() {
        return iotTagId;
    }

    public void setIotTagId(Long iotTagId) {
        this.iotTagId = iotTagId;
    }

    public Long getDayQuantity() {
        return dayQuantity;
    }

    public void setDayQuantity(Long dayQuantity) {
        this.dayQuantity = dayQuantity;
    }
}
