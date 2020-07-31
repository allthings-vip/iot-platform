package allthings.iot.ktv.common.dto;

/**
 * @author tyf
 * @date 2019/08/01 14:55:24
 */
public class PageQueryDto extends QueryDto {

    private Integer pageSize;

    private Integer pageIndex;

    private Long endRowTime;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getEndRowTime() {
        return endRowTime;
    }

    public void setEndRowTime(Long endRowTime) {
        this.endRowTime = endRowTime;
    }

    @Override
    public String toString() {
        return "PageQueryDto{" +
                "pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", endRowTime=" + endRowTime +
                "} " + super.toString();
    }
}
