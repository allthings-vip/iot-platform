package allthings.iot.dos.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  AbstractIotDosDTO
 * @CreateDate :  2018/4/29
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
public abstract class AbstractIotDosDTO implements Serializable {
    /**
     * 操作人ID
     */
    private Long createOperatorId;

    /**
     * 修改人ID
     */
    private Long modifyOperatorId;

    /**
     * 操作员
     */
    private String createOperator;

    /**
     * 创建时间
     */
    private Long inputDate;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 删除标识 0=有效，1=删除
     */
    private Boolean isDeleted;

    public AbstractIotDosDTO() {
    }

    public AbstractIotDosDTO(String createOperator, Long inputDate) {
        this.createOperator = createOperator;
        this.inputDate = inputDate;
    }

    public AbstractIotDosDTO(Long createOperatorId, Long modifyOperatorId) {
        this.createOperatorId = createOperatorId;
        this.modifyOperatorId = modifyOperatorId;
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

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        if (inputDate != null) {
            this.inputDate = inputDate.getTime();
        }
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
