package allthings.iot.dos.dto;

import allthings.iot.dos.dto.query.IotUserQueryDTO;

import java.util.Date;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProjectDTO
 * @CreateDate :  2018/5/4
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
public class IotProjectDTO extends AbstractIotDosDTO {
    /**
     * 项目唯一编码
     */
    private Long iotProjectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 所属单位
     */
    private String companyName;

    /**
     * 简介
     */
    private String description;

    /**
     * 图片
     */
    private String imageUrl;

    /**
     * 协作人员id
     */
    private Long[] iotUserIds;

    /**
     * 协作人员信息
     */
    private List<IotUserQueryDTO> iotUserList;

    /**
     * AppKey
     */
    private String clientId;

    public IotProjectDTO() {
    }

    public IotProjectDTO(Long iotProjectId, String projectName, String companyName,
                         String description, String imageUrl, String createOperator,
                         Date inputDate, String clientId) {
        super(createOperator, inputDate.getTime());
        this.iotProjectId = iotProjectId;
        this.projectName = projectName;
        this.companyName = companyName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.clientId = clientId;
    }

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

    public Long[] getIotUserIds() {
        return iotUserIds;
    }

    public void setIotUserIds(Long[] iotUserIds) {
        this.iotUserIds = iotUserIds;
    }

    public List<IotUserQueryDTO> getIotUserList() {
        return iotUserList;
    }

    public void setIotUserList(List<IotUserQueryDTO> iotUserList) {
        this.iotUserList = iotUserList;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
