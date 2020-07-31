package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotDeviceListBaseQueryDTO
 * @CreateDate :  2019/8/14
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
public class IotDeviceListBaseQueryDTO implements Serializable {

    /**
     * 項目ID
     */
    private Long iotProjectId;
    /**
     * 匹配字段名列表
     */
    private List<String> fields;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 在线状态
     */
    private Boolean status;
    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 是否模糊 true为模糊，
     */
    private Boolean vague;

    private Integer pageSize;

    private Integer pageIndex;

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getVague() {
        return vague;
    }

    public void setVague(Boolean vague) {
        this.vague = vague;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
