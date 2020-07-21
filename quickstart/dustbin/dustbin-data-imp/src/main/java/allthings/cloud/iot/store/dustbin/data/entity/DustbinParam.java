package tf56.cloud.iot.store.dustbin.data.entity;


import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author :  sylar
 * @FileName :  DustbinParam
 * @CreateDate :  2017/11/08
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
@Entity
@Table(appliesTo = "DustbinParam", comment = "参数表")
public class DustbinParam extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = " int(20) comment 'dustbinParamId'")
    private Long dustbinParamId;

    /**
     * 高度
     */
    @Column(columnDefinition = " int comment '高度'")
    private Integer height;

    /**
     * 全满阈值
     */
    @Column(columnDefinition = " int comment '全满阈值'")
    private Integer fullThreshold;

    /**
     * 半满阈值
     */
    @Column(columnDefinition = " int comment '半满阈值'")
    private Integer halfFullThreshold;

    @Column(columnDefinition = " varchar(100) comment '用户id'")
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
