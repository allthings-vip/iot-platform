package allthings.iot.dos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :  txw
 * @FileName :  IotExternalDevicePlatform
 * @CreateDate :  2019/5/9
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
@Table(name = "iot_dos_external_device_platform")
public class IotExternalDevicePlatform implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '服务信息自增主键' ", name = "iot_dos_external_device_platform_id")
    private Long iotExternalDevicePlatformId;

    @Column(columnDefinition = " datetime DEFAULT CURRENT_TIMESTAMP comment '数据创建时间'", name = "gmt_create")
    private Date inputDate = new Date();

    @Column(columnDefinition = " timeStamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT " +
            "'大数据所需要的日期字段,记录更新时间' ", name = "gmt_modified")
    private Date stampDate = new Date();

    @Column(columnDefinition = " tinyint(1) not null default 0 COMMENT '删除标识 1=删除 0=有效'", name = "is_deleted")
    private boolean isDeleted;

    @Column(columnDefinition = " bigint(19) not null COMMENT '操作员id'", nullable = false, name = "create_operator_id")
    private Long createOperatorId;

    @Column(columnDefinition = "bigint(19) COMMENT '修改人id'", name = "modify_operator_id")
    private Long modifyOperatorId;

    @Column(columnDefinition = " varchar(500) comment '平台名称' ", name = "platform_name", nullable = false)
    private String platformName;

    @Column(columnDefinition = " varchar(500) comment '测试接口地址' ", name = "platform_code", nullable = false)
    private String platformCode;

    @Column(columnDefinition = " varchar(500) comment '依赖服务' ", name = "dependency_service")
    private String dependencyService;

    @Column(columnDefinition = " tinyint(1) COMMENT '启停状态 1=启动 0=停止'", name = "status")
    private Boolean status;

    public Long getIotExternalDevicePlatformId() {
        return iotExternalDevicePlatformId;
    }

    public void setIotExternalDevicePlatformId(Long iotExternalDevicePlatformId) {
        this.iotExternalDevicePlatformId = iotExternalDevicePlatformId;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getCreateOperatorId() {
        return createOperatorId;
    }

    public void setCreateOperatorId(Long createOperatorId) {
        this.createOperatorId = createOperatorId;
    }

    public Long getModifyOperatorId() {
        return modifyOperatorId;
    }

    public void setModifyOperatorId(Long modifyOperatorId) {
        this.modifyOperatorId = modifyOperatorId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getDependencyService() {
        return dependencyService;
    }

    public void setDependencyService(String dependencyService) {
        this.dependencyService = dependencyService;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
