/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.constant.gps.GpsMsgParam;

/**
 * 类UpExgMsg.java的实现描述：TODO 类实现描述
 *
 * @author mingyuan.miao 2018年3月20日 下午12:24:23
 */
public class UpExgMsg extends MsgPackage {

    private String vehicleNo;
    private byte vehicleColor;
    private short dataType;
    private int dataLength;
    private Object subData;

    /**
     * @param buffer
     */
    public UpExgMsg(ByteBuffer buffer) {
        super(buffer);
    }

    /* (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.UnPackable#unPack(byte[])
     */
    @Override
    public void unPack(byte[] bytes) {
        ByteBuf bodyBuf = Unpooled.wrappedBuffer(bytes);
        byte[] tmp = new byte[21];
        bodyBuf.readBytes(tmp);
        try {
            vehicleNo = new String(tmp, "GBK").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        vehicleColor = bodyBuf.readByte();
        dataType = bodyBuf.readShort();
        dataLength = bodyBuf.readInt();

        tmp = new byte[dataLength];
        bodyBuf.readBytes(tmp);

        UnPackable msgBody = null;
        switch (dataType) {
            case 0x1202:
                msgBody = new RealLocationUpExgMsgBody();
                msgBody.unPack(tmp);
                break;
            default:
                break;
        }
        this.subData = msgBody;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public byte getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(byte vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public short getDataType() {
        return dataType;
    }

    public void setDataType(short dataType) {
        this.dataType = dataType;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public Object getSubData() {
        return subData;
    }

    public void setSubData(Object subData) {
        this.subData = subData;
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getMsgCode()
     */
    @Override
    public String getMsgCode() {
        return dataType + "";
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getDeviceId()
     */
    @Override
    public String getDeviceId() {
        return vehicleNo;
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getMsgBody()
     */
    @Override
    public Map<String, Object> getMsgBody() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vehicleNo", vehicleNo);
        map.put("vehicleColor", vehicleColor);
        map.put("dataType", dataType);
        map.put("dataLength", dataLength);
        if (subData instanceof RealLocationUpExgMsgBody) {
            RealLocationUpExgMsgBody subBody = (RealLocationUpExgMsgBody) subData;
            map.put("excrypt", subBody.getExcrypt());
            map.put(GpsMsgParam.ATTR_GPS_DATETIME, subBody.getDate());
            map.put(GpsMsgParam.ATTR_GPS_LONGITUDE, subBody.getLon());
            map.put(GpsMsgParam.ATTR_GPS_LATITUDE, subBody.getLat());
            map.put(GpsMsgParam.ATTR_GPS_SPEED, subBody.getVec1());
            map.put(GpsMsgParam.ATTR_CURRENT_SPEED, subBody.getVec2());
            map.put(GpsMsgParam.ATTR_GPS_MILEAGE, subBody.getVec3());
            map.put(GpsMsgParam.ATTR_GPS_DIRECTION, subBody.getDirection());
            map.put(GpsMsgParam.ATTR_GPS_ALTITUDE, subBody.getAltitude());
            map.put(GpsMsgParam.GPS_VALID, subBody.isGpsValid());
            map.put(GpsMsgParam.ATTR_IGNITION, subBody.isStateIgnition());
            map.put(GpsMsgParam.ATTR_GPS_STATUS,subBody.getState());
            map.put(GpsMsgParam.ATTR_GPS_ALARM, subBody.getAlarm());
        }
        return map;
    }
}
