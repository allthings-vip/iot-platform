package allthings.iot.ktv.common.dto;

import java.io.Serializable;

public class KvsDto  implements Serializable {

    /**
     * 因子编码
     */
    private String factorId;
    /**
     * 因子数值
     */
    private String factorValue;
    /**
     * 采集时间
     */
    private long realTime;

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
    }

    public long getRealTime() {
        return realTime;
    }

    public void setRealTime(long realTime) {
        this.realTime = realTime;
    }
}
