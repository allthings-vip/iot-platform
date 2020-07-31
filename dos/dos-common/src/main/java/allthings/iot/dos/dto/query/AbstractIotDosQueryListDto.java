package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

import java.io.Serializable;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-10-31 11:16
 */

public abstract class AbstractIotDosQueryListDto extends AbstractIotDosDTO implements Serializable {
    /**
     * 页码数
     */
    private Integer pageIndex;

    /**
     * 页码条数
     */
    private Integer pageSize;

    /**
     * 关键词
     */
    private String keywords;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
