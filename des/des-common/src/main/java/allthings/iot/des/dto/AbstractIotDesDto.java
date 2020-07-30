package allthings.iot.des.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tyf
 * @date 2019/03/04 15:00
 */
public abstract class AbstractIotDesDto implements Serializable {

    private Date gmtCreate;

    private Date gmtModified;

    private Long createOperatorId;

    private Long modifyOperatorId;

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
