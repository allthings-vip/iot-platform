package allthings.iot.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-07-01 10:31
 */

public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -4942430187181201793L;
    private int total;
    private List<T> data;

    public PageResult(int total, List<T> data) {
        if (total < 0) {
            throw new IllegalArgumentException("[total] must great than zero");
        } else {
            this.total = total;
            this.data = data;
        }
    }

    public PageResult() {
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return this.total;
    }

    public List<T> getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "PageResult{total=" + this.total + ", data=" + this.data + '}';
    }
}
