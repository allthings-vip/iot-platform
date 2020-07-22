package allthings.iot.common.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  QueryResult
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
public class QueryResult<T> implements Serializable {

    private long rowCount;
    private List<T> items;

    public QueryResult(List<T> items, long rowCount) {
        this.items = items;
        this.rowCount = rowCount;
    }

    public QueryResult() {
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}

