package allthings.iot.des.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tyf
 * @date 2019/03/04 10:38
 */
public abstract class AbstractIotDesModel implements Serializable {
    /**
     * 数据创建时间
     */
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    private Date gmtModified;

    /**
     * 删除标识 0=有效，1=删除
     */
    private Boolean isDeleted;

    /**
     * 创建者id
     */
    private Long createOperatorId;

    /**
     * 修改者id
     */
    private Long modifyOperatorId;

    /**
     * 乐观锁
     */
    private Integer updateVersion;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
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

    public Integer getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(Integer updateVersion) {
        this.updateVersion = updateVersion;
    }
}
