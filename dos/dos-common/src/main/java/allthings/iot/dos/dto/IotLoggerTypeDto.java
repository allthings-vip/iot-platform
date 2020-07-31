package allthings.iot.dos.dto;

/**
 * @author tyf
 * @date 2019/07/05 10:36:12
 */
public class IotLoggerTypeDto extends AbstractIotDosDTO {

    private Long iotLoggerTypeId;

    private String loggerTypeCode;

    private String loggerTypeName;

    public Long getIotLoggerTypeId() {
        return iotLoggerTypeId;
    }

    public void setIotLoggerTypeId(Long iotLoggerTypeId) {
        this.iotLoggerTypeId = iotLoggerTypeId;
    }

    public String getLoggerTypeCode() {
        return loggerTypeCode;
    }

    public void setLoggerTypeCode(String loggerTypeCode) {
        this.loggerTypeCode = loggerTypeCode;
    }

    public String getLoggerTypeName() {
        return loggerTypeName;
    }

    public void setLoggerTypeName(String loggerTypeName) {
        this.loggerTypeName = loggerTypeName;
    }

}
