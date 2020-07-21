/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.util.misc.ByteUtils;

import java.nio.ByteBuffer;

/**
 * 类MsgPackageBuilder.java的实现描述：TODO 类实现描述
 *
 * @author mingyuan.miao 2018年3月22日 下午7:48:28
 */
public class MsgPackageBuilder {

    public static MsgPackage build(ByteBuffer buffer) {
        ByteBuf bodyBuf = Unpooled.wrappedBuffer(buffer);
        byte[] msgIdBytes = new byte[2];
        bodyBuf.getBytes(9,msgIdBytes);
        String msgId = ByteUtils.bytesToHexString(msgIdBytes);

        switch (msgId) {
            case MsgType.UP_CONNECT_REQ:
                return new UpConnectReq(buffer);
            case MsgType.UP_DISCONNECT_REQ:
                return new UpDisConnectReq(buffer);
            case MsgType.UP_EXG_MSG:
                return new UpExgMsg(buffer);
            default:
                return null;
        }

    }
}
