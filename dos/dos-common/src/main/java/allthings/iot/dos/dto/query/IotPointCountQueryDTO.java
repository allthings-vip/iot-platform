package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotPointCountQueryDTO
 * @CreateDate :  2018-5-20
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
public class IotPointCountQueryDTO implements Serializable {
    /**
     * 点位数量
     */
    private Long pointCounts;
    /**
     * 时间
     */
    private Long inputDate;

    public IotPointCountQueryDTO() {
    }

    public IotPointCountQueryDTO(Long pointCounts, Date inputDate) {
        this.pointCounts = pointCounts;
        this.inputDate = inputDate.getTime();
    }

    public Long getPointCounts() {
        return pointCounts;
    }

    public void setPointCounts(Long pointCounts) {
        this.pointCounts = pointCounts;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }
}
