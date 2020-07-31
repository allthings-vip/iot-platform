package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotProject
 * @CreateDate :  2018/4/26
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
@Table(name = "iot_dos_project")
public class IotProject extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '项目唯一编码' ", name = "iot_dos_project_id")
    private Long iotProjectId;

    @Column(columnDefinition = " varchar(500)  comment '项目名称' ", name = "project_name", nullable = false)
    private String projectName;

    @Column(columnDefinition = " varchar(500)  comment '所属单位' ", name = "company_name", nullable = false)
    private String companyName;

    @Column(columnDefinition = " text comment '简介' ")
    private String description;

    @Column(columnDefinition = " varchar(500)  comment '图片' ", name = "image_url", nullable = false)
    private String imageUrl;

    @Column(columnDefinition = "tinyint(1) comment ''", name = "is_review")
    private Boolean isReview;

    @Column(columnDefinition = "varchar(500)  comment 'AppKey' ", name = "client_id")
    private String clientId;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getReview() {
        return isReview;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
