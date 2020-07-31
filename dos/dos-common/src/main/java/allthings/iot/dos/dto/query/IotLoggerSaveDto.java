package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.AbstractIotDosDTO;

/**
 * @author tyf
 * @date 2019/03/12 18:13
 */
public class IotLoggerSaveDto extends AbstractIotDosDTO {

    private Long iotLoggerId;

    private Long loggerTime;

    private Long iotUserId;

    private String loggerContent;

    private Long iotLoggerTypeId;

    private Long iotLoggerRelationId;

    private String associationType;

    private Long associationId;

    private Long iotProjectId;

    public IotLoggerSaveDto() {
    }

    public IotLoggerSaveDto(Long createOperatorId, Long modifyOperatorId, Long loggerTime, Long iotUserId,
                            Long iotLoggerTypeId, String associationType, Long associationId, Long iotProjectId) {
        super(createOperatorId, modifyOperatorId);
        this.loggerTime = loggerTime;
        this.iotUserId = iotUserId;
        this.iotLoggerTypeId = iotLoggerTypeId;
        this.associationType = associationType;
        this.associationId = associationId;
        this.iotProjectId = iotProjectId;
    }

    public Long getIotLoggerId() {
        return iotLoggerId;
    }

    public void setIotLoggerId(Long iotLoggerId) {
        this.iotLoggerId = iotLoggerId;
    }

    public Long getLoggerTime() {
        return loggerTime;
    }

    public void setLoggerTime(Long loggerTime) {
        this.loggerTime = loggerTime;
    }

    public Long getIotUserId() {
        return iotUserId;
    }

    public void setIotUserId(Long iotUserId) {
        this.iotUserId = iotUserId;
    }

    public String getLoggerContent() {
        return loggerContent;
    }

    public void setLoggerContent(String loggerContent) {
        this.loggerContent = loggerContent;
    }

    public Long getIotLoggerTypeId() {
        return iotLoggerTypeId;
    }

    public void setIotLoggerTypeId(Long iotLoggerTypeId) {
        this.iotLoggerTypeId = iotLoggerTypeId;
    }

    public Long getIotLoggerRelationId() {
        return iotLoggerRelationId;
    }

    public void setIotLoggerRelationId(Long iotLoggerRelationId) {
        this.iotLoggerRelationId = iotLoggerRelationId;
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}
