package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  AbstractIotDosModel
 * @CreateDate :  2018/04/25
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
@MappedSuperclass
public abstract class AbstractIotDosModel implements Serializable {
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

    public AbstractIotDosModel() {
    }

    public AbstractIotDosModel(Date inputDate, Long createOperatorId) {
        this.inputDate = inputDate;
        this.createOperatorId = createOperatorId;
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
}
