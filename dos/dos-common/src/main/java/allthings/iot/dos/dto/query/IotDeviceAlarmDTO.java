package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceAlarmDTO
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
public class IotDeviceAlarmDTO implements Serializable {
    /**
     * 报警内容
     */
    private String alarmContent;
    /**
     * 报警源
     */
    private String alarmSource;
    /**
     * 报警类型
     */
    private String alarmType;
    /**
     * 发生时间
     */
    private Long inputDate;

    public IotDeviceAlarmDTO() {
    }

    public IotDeviceAlarmDTO(String alarmContent, String alarmSource, String alarmType, Long inputDate) {
        this.alarmContent = alarmContent;
        this.alarmSource = alarmSource;
        this.alarmType = alarmType;
        this.inputDate = inputDate;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public Long getInputDate() {
        return inputDate;
    }

    public void setInputDate(Long inputDate) {
        this.inputDate = inputDate;
    }
}
