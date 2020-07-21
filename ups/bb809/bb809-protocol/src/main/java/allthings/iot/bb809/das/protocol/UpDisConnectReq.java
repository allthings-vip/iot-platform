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

/**
 * 类UpDisConnectReq.java的实现描述：TODO 类实现描述 
 * @author mingyuan.miao 2018年3月20日 下午9:36:48
 */
public class UpDisConnectReq extends MsgPackage {

    private int    userId;
    private String password;

    /**
     * @param buffer
     */
    public UpDisConnectReq(ByteBuffer buffer){
        super(buffer);
    }

    /* (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.UnPackable#unPack(byte[])
     */
    @Override
    public void unPack(byte[] bytes) {
        ByteBuf bodyBuf = Unpooled.wrappedBuffer(bytes);
        byte[] passwordBytes = new byte[8];
        userId = bodyBuf.readInt();
        bodyBuf.readBytes(passwordBytes);
        try {
            password = new String(passwordBytes, "GBK").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getMsgCode()
     */
    @Override
    public String getMsgCode() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getDeviceId()
     */
    @Override
    public String getDeviceId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getMsgBody()
     */
    @Override
    public Map<String, Object> getMsgBody() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("password", password);
        return map;
    }

}
