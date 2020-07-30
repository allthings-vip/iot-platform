package allthings.iot.des.dto;

/**
 * @author tyf
 * @date 2019/03/13 16:45
 */
public class IotDesEventTypeDto extends AbstractIotDesDto {
    /**
     * 事件类型id
     */
    private Long iotDesEventTypeId;

    /**
     * 事件类型编码
     */
    private String eventTypeCode;

    /**
     * 事件类型名称
     */
    private String eventTypeName;

    public Long getIotDesEventTypeId() {
        return iotDesEventTypeId;
    }

    public void setIotDesEventTypeId(Long iotDesEventTypeId) {
        this.iotDesEventTypeId = iotDesEventTypeId;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
