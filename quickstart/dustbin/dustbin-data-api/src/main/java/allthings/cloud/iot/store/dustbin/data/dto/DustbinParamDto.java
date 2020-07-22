package tf56.cloud.iot.store.dustbin.data.dto;

/**
 * @author :  sylar
 * @FileName :  DustbinParamDto
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class DustbinParamDto extends BaseDto {
    private Long dustbinParamId;
    /**
     * 高度
     */
    private Integer height;

    /**
     * 全满阈值
     */
    private Integer fullThreshold;

    /**
     * 半满阈值
     */
    private Integer halfFullThreshold;

    private String userId;

    public Long getDustbinParamId() {
        return dustbinParamId;
    }

    public void setDustbinParamId(Long dustbinParamId) {
        this.dustbinParamId = dustbinParamId;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFullThreshold() {
        return fullThreshold;
    }

    public void setFullThreshold(Integer fullThreshold) {
        this.fullThreshold = fullThreshold;
    }

    public Integer getHalfFullThreshold() {
        return halfFullThreshold;
    }

    public void setHalfFullThreshold(Integer halfFullThreshold) {
        this.halfFullThreshold = halfFullThreshold;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
