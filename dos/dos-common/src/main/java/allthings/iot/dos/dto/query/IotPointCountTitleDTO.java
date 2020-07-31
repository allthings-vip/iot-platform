package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotPointCountTitleDTO
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
public class IotPointCountTitleDTO implements Serializable {
    /**
     * 点位数量
     */
    private Long pointCounts;
    /**
     * 标题
     */
    private String title;

    public IotPointCountTitleDTO() {
    }

    public IotPointCountTitleDTO(Long pointCounts, String title) {
        this.pointCounts = pointCounts;
        this.title = title;
    }

    public Long getPointCounts() {
        return pointCounts;
    }

    public void setPointCounts(Long pointCounts) {
        this.pointCounts = pointCounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
