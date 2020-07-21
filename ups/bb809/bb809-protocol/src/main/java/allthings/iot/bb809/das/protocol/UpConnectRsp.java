/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 类UpConnectRsp.java的实现描述：TODO 类实现描述 
 * @author mingyuan.miao 2018年3月22日 下午4:46:57
 */
public class UpConnectRsp implements Packable {

    public static final byte RESULT_SUCCESS            = 0x00;
    public static final byte RESULT_IP_ERROR           = 0x01;
    public static final byte RESULT_GNSSCENTERID_ERROR = 0x02;
    public static final byte RESULT_USER_ERROR         = 0x03;
    public static final byte RESULT_PASSWORD_ERROR     = 0x04;
    public static final byte RESULT_USAGE_ERROR        = 0x05;
    public static final byte RESULT_OTHER              = 0x06;
    private byte             result                    = RESULT_SUCCESS;
    private int              verifyCode                = 0x00000000;

    /* (non-Javadoc)
     * @see allthings.iot.bb809.das.protocol.Packable#pack()
     */
    @Override
    public byte[] pack() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5).order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(result);
        byteBuffer.putInt(verifyCode);
        return byteBuffer.array();
    }

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

}
