package allthings.iot.dos.dto.query;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotEventQueryListDTO
 * @CreateDate :  2018/11/7
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IotEventQueryListDTO extends AbstractIotDosQueryListDto {

    private String deviceCode;

    private Long startDatetime;

    private Long endDatetime;

    private String disposeStatus;

    private Long iotProjectId;

    private List<String> eventCodes;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getDisposeStatus() {
        return disposeStatus;
    }

    public void setDisposeStatus(String disposeStatus) {
        this.disposeStatus = disposeStatus;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public List<String> getEventCodes() {
        return eventCodes;
    }

    public void setEventCodes(List<String> eventCodes) {
        this.eventCodes = eventCodes;
    }
}
