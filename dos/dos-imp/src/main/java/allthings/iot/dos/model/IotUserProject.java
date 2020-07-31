package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-10-30 13:49
 */
@Entity
@Table(name = "iot_dos_user_project")
public class IotUserProject extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '唯一标识' ", name = "iot_dos_user_project_id")
    private Long iotUserProjectId;

    @Column(columnDefinition = " int(20) comment '项目ID' ", nullable = false, name = "iot_dos_project_id")
    private Long iotProjectId;

    @Column(columnDefinition = " int(20) comment '用户id' ", nullable = false, name = "iot_dos_user_id")
    private Long iotUserId;

    @Column(columnDefinition = " tinyint(1) comment '是否拥有者' ", name = "is_owner")
    private Boolean isOwner;

    public Long getIotUserProjectId() {
        return iotUserProjectId;
    }

    public void setIotUserProjectId(Long iotUserProjectId) {
        this.iotUserProjectId = iotUserProjectId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public Long getIotUserId() {
        return iotUserId;
    }

    public void setIotUserId(Long iotUserId) {
        this.iotUserId = iotUserId;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
