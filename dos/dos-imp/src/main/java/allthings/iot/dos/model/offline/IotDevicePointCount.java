package allthings.iot.dos.model.offline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-27 14:01
 */
@Entity
@Table(name = "iot_dos_device_point_count")
public class IotDevicePointCount extends AbstractIotDosOfflineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(20) comment '数据点位数量自增主键' ", name = "iot_dos_device_point_count_id")
    private Long iotDevicePointCountId;

    @Column(columnDefinition = "varchar(500) not null  comment 'deviceID'", nullable = false, name = "device_id")
    private String deviceId;

    @Column(columnDefinition = "int(20) comment '当天之前总数量' ", nullable = false, name = "day_before_quantity")
    private Long dayBeforeQuantity;

    @Column(columnDefinition = " datetime DEFAULT CURRENT_TIMESTAMP comment '截至日期'", name = "up_date_time")
    private Date upDateTime;

    public Long getIotDevicePointCountId() {
        return iotDevicePointCountId;
    }

    public void setIotDevicePointCountId(Long iotDevicePointCountId) {
        this.iotDevicePointCountId = iotDevicePointCountId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDayBeforeQuantity() {
        return dayBeforeQuantity;
    }

    public void setDayBeforeQuantity(Long dayBeforeQuantity) {
        this.dayBeforeQuantity = dayBeforeQuantity;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }
}
