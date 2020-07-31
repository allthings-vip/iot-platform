package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceEventDTO
 * @CreateDate :  2018-5-12
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
public class IotDeviceEventDTO implements Serializable {
    /**
     * 事件内容
     */
    private String eventContent;
    /**
     * 事件源
     */
    private String eventSource;
    /**
     * 事件类型
     */
    private String eventType;
    /**
     * 发生时间
     */
    private Long inputDate;

    public IotDeviceEventDTO() {
    }

    public IotDeviceEventDTO(String eventContent, String eventSource, String eventType, Long inputDate) {
        this.eventContent = eventContent;
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.inputDate = inputDate;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }
}
