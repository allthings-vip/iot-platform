package allthings.iot.dos.dto.open;

/**
 * @author tyf
 * @date 2019/08/12 09:43:53
 */
public class IotTrackPageQueryDto extends allthings.iot.dos.open.dto.IotTrackQueryListDTO {

    private Long dateTime;

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "IotTrackPageQueryDto{" +
                "dateTime=" + dateTime +
                "} " + super.toString();
    }
}
