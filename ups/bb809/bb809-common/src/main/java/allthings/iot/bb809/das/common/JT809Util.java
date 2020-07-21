/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.bb809.das.common;

import allthings.iot.util.misc.ByteUtils;

/**
 * 类JT809Util.java的实现描述：TODO 类实现描述 
 * @author mingyuan.miao 2018年3月20日 下午2:44:39
 */
public class JT809Util {

    private static final int  m1  = 10000000;
    private static final int  a1  = 20000000;
    private static final int  c1  = 30000000;

    private final static long MAX = (1L << 32) - 1;

    public static String encrypt(long key, String str) {
        byte[] b = ByteUtils.hexStringToBytes(str);
        int size = b.length;
        if (key == 0) key = 1;
        int i = 0;
        while (i < size) {
            key = (a1 * (key % m1) + c1) & MAX;
            short d = (short) ((key >> 20) & 0xFF);
            b[i++] ^= d;
        }
        return ByteUtils.bytesToHexString(b);
    }

}
