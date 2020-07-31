package allthings.iot.dos.dto.open;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2019/08/12 09:38:02
 */
public class IotGpsPageDto implements Serializable {

    private List<IotGpsDTO> dateList;

    private Boolean pageDown;

    public IotGpsPageDto() {
    }

    public IotGpsPageDto(List<IotGpsDTO> dateList, Boolean pageDown) {
        this.dateList = dateList;
        this.pageDown = pageDown;
    }

    public List<IotGpsDTO> getDateList() {
        return dateList;
    }

    public void setDateList(List<IotGpsDTO> dateList) {
        this.dateList = dateList;
    }

    public Boolean getPageDown() {
        return pageDown;
    }

    public void setPageDown(Boolean pageDown) {
        this.pageDown = pageDown;
    }

    @Override
    public String toString() {
        return "IotGpsPageDto{" +
                "dateList=" + dateList +
                ", pageDown=" + pageDown +
                '}';
    }
}
