package allthings.iot.dos.dto.query;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProjectQueryDTO
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
public class IotProjectQueryDTO extends IotProjectSimpleDTO {
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
     * 设备总数
     */
    private Long deviceTotal;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 是否审核
     */
    private Boolean isReview;

    /**
     * 设备类型名称
     */
    private List<IotProjectDeviceTypeDTO> deviceTypes;

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

    public Long getDeviceTotal() {
        return deviceTotal;
    }

    public void setDeviceTotal(Long deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getReview() {
        return isReview;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }

    public List<IotProjectDeviceTypeDTO> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(List<IotProjectDeviceTypeDTO> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
}
