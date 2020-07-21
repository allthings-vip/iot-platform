/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.util.misc.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * 类UpConnectReq.java的实现描述：TODO 类实现描述
 *
 * @author mingyuan.miao 2018年3月20日 下午9:32:25
 */
public class UpConnectReq extends MsgPackage {

    private int userId;
    private String password;
    private String downLinkIp;
    private short downLinkPort;

    public UpConnectReq(ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void unPack(byte[] bytes) {
        ByteBuf bodyBuf = Unpooled.wrappedBuffer(bytes);

        userId = bodyBuf.readInt();

        byte[] passwordBytes = new byte[8];
        bodyBuf.readBytes(passwordBytes);
        password = ByteUtils.toString(passwordBytes, 0, passwordBytes.length);

        byte[] downLinkIpBytes = new byte[32];
        bodyBuf.readBytes(downLinkIpBytes);
        downLinkIp = ByteUtils.toString(downLinkIpBytes, 0, downLinkIpBytes.length);

        downLinkPort = bodyBuf.readShort();
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

    public String getDownLinkIp() {
        return downLinkIp;
    }

    public void setDownLinkIp(String downLinkIp) {
        this.downLinkIp = downLinkIp;
    }

    public short getDownLinkPort() {
        return downLinkPort;
    }

    public void setDownLinkPort(short downLinkPort) {
        this.downLinkPort = downLinkPort;
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.MsgPackage#getMsgCode()
     */
    @Override
    public String getMsgCode() {
        return getMsgHeader().getMsgId();
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
        Map<String, Object> msgBody = Maps.newHashMap();
        msgBody.put("userId", userId);
        msgBody.put("password", password);
        msgBody.put("downLinkIp", downLinkIp);
        msgBody.put("downLinkPort", downLinkPort);

        return msgBody;
    }

}
