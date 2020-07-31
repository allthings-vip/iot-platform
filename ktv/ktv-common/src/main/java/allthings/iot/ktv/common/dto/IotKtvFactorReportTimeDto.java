package allthings.iot.ktv.common.dto;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2018/11/07 10:04
 */
public class IotKtvFactorReportTimeDto implements Serializable {

    /**
     * 设备编码
     */
    private String deviceId;

    /**
     * 因子编码
     */
    private String factorCode;

    /**
     * 上传时间
     */
    private Long reportTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFactorCode() {
        return factorCode;
    }

    public void setFactorCode(String factorCode) {
        this.factorCode = factorCode;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public IotKtvFactorReportTimeDto() {
    }

    public IotKtvFactorReportTimeDto(String deviceId, String factorCode, Long reportTime) {
        this.deviceId = deviceId;
        this.factorCode = factorCode;
        this.reportTime = reportTime;
    }

    public IotKtvFactorReportTimeDto(String deviceId, Long reportTime) {
        this.deviceId = deviceId;
        this.reportTime = reportTime;
    }
}
