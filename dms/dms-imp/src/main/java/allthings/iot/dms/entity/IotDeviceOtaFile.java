package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :  sylar
 * @FileName :  IotDeviceOtaFile
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
@Table(appliesTo = "IotDeviceOtaFile", comment = "设备固件表")
public class IotDeviceOtaFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotDeviceOtaFileId' ")
    private Long iotDeviceOtaFileId;


    @Column(nullable = false, columnDefinition = " varchar(255) comment '设备类型' ")
    private String deviceType;

    @Column(nullable = false, columnDefinition = "  int(10) comment '固件版本号' ")
    private int versionCode;

    @Column(columnDefinition = "  varchar(255) comment '固件版本名称' ")
    private String versionName;

    @Column(nullable = false, columnDefinition = " varchar(255) comment '文件路径' ")
    private String filePath;

    @Column(columnDefinition = " varchar(255) comment '文件名称' ")
    private String fileName;

    @Column(columnDefinition = "  varchar(255) comment '固件描述' ")
    private String description;

    @Column(columnDefinition = " int(10) comment '下载次数' ")
    private Integer downloadCount;

    @Column(columnDefinition = " datetime comment '数据创建时间'")
    private Date inputDate = new Date();

    @Column(columnDefinition = " datetime comment '记录修改时间' ")
    private Date updateDate = new Date();

    @Column(columnDefinition = " timestamp not null COMMENT '大数据所需要的日期字段,记录更新时间' ")
    private Date stampDate;

    @Column(columnDefinition = " char(1) comment '是否逻辑删除'")
    @Type(type = "yes_no")
    private boolean isDelete;

    public Long getIotDeviceOtaFileId() {
        return iotDeviceOtaFileId;
    }

    public void setIotDeviceOtaFileId(Long iotDeviceOtaFileId) {
        this.iotDeviceOtaFileId = iotDeviceOtaFileId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
