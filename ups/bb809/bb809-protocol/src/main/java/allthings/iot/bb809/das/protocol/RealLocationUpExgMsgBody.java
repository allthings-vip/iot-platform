/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.util.misc.ByteUtils;

/**
 * 类RealLocationUpExgMsgBody.java的实现描述：TODO 类实现描述
 *
 * @author mingyuan.miao 2018年3月20日 下午12:25:28
 */
public class RealLocationUpExgMsgBody implements UnPackable {

    private byte excrypt;
    private Date date;
    private double lon;       // 经度
    private double lat;       // 纬度
    private int vec1;              // 速度，卫星定位
    private int vec2;              // 行驶记录速度
    private long vec3;      // 总里程数
    private int direction;         // 方向
    private int altitude;          // 海拔高度
    private long state;             // 车辆状态
    private long alarm;             // 报警状态
    /**
     * 车辆点火状态
     */
    private boolean stateIgnition = false;
    /**
     * GPS定位已否
     */
    private boolean gpsValid = false;
    /**
     * 是否北纬
     */
    private boolean isNorth = true;
    /**
     * 是否东经
     */
    private boolean isEast = true;

    private Date getForDateTime(byte[] date, byte[] time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, ByteUtils.toInt16(date, 2, ByteOrder.BIG_ENDIAN));
        calendar.set(Calendar.MONTH, ByteUtils.toInt(date[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, ByteUtils.toInt(date[0]));
        calendar.set(Calendar.HOUR_OF_DAY, ByteUtils.toInt(time[0]));
        calendar.set(Calendar.MINUTE, ByteUtils.toInt(time[1]));
        calendar.set(Calendar.SECOND, ByteUtils.toInt(time[2]));
        return calendar.getTime();
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.UnPackable#unPack(byte[])
     */
    @Override
    public void unPack(byte[] bytes) {
        ByteBuf bodyBuf = Unpooled.wrappedBuffer(bytes);
        excrypt = bodyBuf.readByte();
        // 日月年（dmyy）
        byte[] dateBytes = new byte[4];
        // 时分秒（hms）
        byte[] timeBytes = new byte[3];
        bodyBuf.readBytes(dateBytes);
        bodyBuf.readBytes(timeBytes);
        date = getForDateTime(dateBytes, timeBytes);
        lon = bodyBuf.readUnsignedInt() / 1000000D;
        lat = bodyBuf.readUnsignedInt() / 1000000D;
        vec1 = bodyBuf.readUnsignedShort();
        vec2 = bodyBuf.readUnsignedShort();
        vec3 = bodyBuf.readUnsignedInt();
        direction = bodyBuf.readShort();
        altitude = bodyBuf.readUnsignedShort();
        state = bodyBuf.readUnsignedInt();
        alarm = bodyBuf.readUnsignedInt();

        unpackState(state);

        if (!isNorth) {
            lat = lat * -1;
        }

        if (!isEast) {
            lon = lon * -1;
        }
    }


    /**
     * 处理状态
     *
     * @param status
     */
    private void unpackState(long status) {
        if ((status & 0x01) != 0) {
            stateIgnition = true;
        }

        // 1 0：未定位；1：定位
        if ((status & 0x02) != 0) {
            gpsValid = true;
        }

        // 2 0：北纬；1：南纬
        // 纬度（正：北纬； 负：南纬）
        if ((status & 0x04) != 0) {
            isNorth = false;
        }

        // 3 0：东经；1：西经
        // 经度（正：东经； 负：西经）
        if ((status & 0x08) != 0) {
            isEast = false;
        }
    }

    public boolean isStateIgnition() {
        return stateIgnition;
    }

    public void setStateIgnition(boolean stateIgnition) {
        this.stateIgnition = stateIgnition;
    }

    public boolean isGpsValid() {
        return gpsValid;
    }

    public void setGpsValid(boolean gpsValid) {
        this.gpsValid = gpsValid;
    }

    public byte getExcrypt() {
        return excrypt;
    }

    public void setExcrypt(byte excrypt) {
        this.excrypt = excrypt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getVec1() {
        return vec1;
    }

    public void setVec1(int vec1) {
        this.vec1 = vec1;
    }

    public int getVec2() {
        return vec2;
    }

    public void setVec2(int vec2) {
        this.vec2 = vec2;
    }

    public long getVec3() {
        return vec3;
    }

    public void setVec3(long vec3) {
        this.vec3 = vec3;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public long getAlarm() {
        return alarm;
    }

    public void setAlarm(long alarm) {
        this.alarm = alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    @Override
    public String toString() {
        return "RealLocationUpExgMsgBody [excrypt=" + excrypt + ", date=" + date + ", lon=" + lon + ", lat=" + lat
                + ", vec1=" + vec1 + ", vec2=" + vec2 + ", vec3=" + vec3 + ", direction=" + direction + ", altitude="
                + altitude + ", state=" + state + ", alarm=" + alarm + "]";
    }

}
