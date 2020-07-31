package allthings.iot.ktv.common.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/08/05 08:51:47
 */
public class PageResultDto implements Serializable {

    private List<Map<String, String>> dataList;

    private boolean pageDown;

    public PageResultDto() {
    }

    public PageResultDto(List<Map<String, String>> dataList, boolean pageDown) {
        this.dataList = dataList;
        this.pageDown = pageDown;
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }

    public boolean isPageDown() {
        return pageDown;
    }

    public void setPageDown(boolean pageDown) {
        this.pageDown = pageDown;
    }


}
