package allthings.iot.dos.dto.oauth2;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  AbstractDosOauth2DTO
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
public abstract class AbstractDosOauth2DTO implements Serializable {
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

    public AbstractDosOauth2DTO() {
    }

    public AbstractDosOauth2DTO(String createOperator, Long inputDate) {
        this.createOperator = createOperator;
        this.inputDate = inputDate;
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
}
