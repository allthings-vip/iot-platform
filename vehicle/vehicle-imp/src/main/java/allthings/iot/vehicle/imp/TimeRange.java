package allthings.iot.vehicle.imp;

/**
 * @author luhao
 * @version v1.0
 * @title TimeRange
 * @date 2020/4/2 16:45
 * @description
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 */
public class TimeRange {
    private long startDatetime;
    private double startOil;
    private long endDatetime;
    private double endOil;

    public TimeRange(long startDatetime, double startOil, long endDatetime, double endOil) {
        this.startDatetime = startDatetime;
        this.startOil = startOil;
        this.endDatetime = endDatetime;
        this.endOil = endOil;
    }

    public long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public double getStartOil() {
        return startOil;
    }

    public void setStartOil(double startOil) {
        this.startOil = startOil;
    }

    public double getEndOil() {
        return endOil;
    }

    public void setEndOil(double endOil) {
        this.endOil = endOil;
    }
}
