package allthings.iot.util.misc;

import com.google.common.base.Charsets;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author :  sylar
 * @FileName :  ByteUtils
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class ByteUtils {

    public static final Charset CHARSET_UTF8 = Charsets.UTF_8;
    public static Charset CHARSET_NAME = Charset.forName("gb2312");
    public static Charset CHARSET_NAME_GBK = Charset.forName("gbk");
    public static final byte[] EMPTY_BYTE = new byte[0];


    /**
     * byte 与 int 的相互转换
     */
    public static byte toByte(int x) {
        return (byte) x;
    }

    public static short toShort(byte b) {
        return (short) toInt(b);
    }

    public static int toInt(byte b) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    public static int toUnsignedInt(byte[] bytes) {
        int ret = 0;
        int size = bytes.length;
        int beginPos = size > 4 ? 4 : size;
        for (int i = 0; i <= beginPos - 1; i++) {
            ret = (ret << 8) | (bytes[i] & 0xff);
        }
        return ret;
    }

    public static short toInt16(byte[] value, int startIndex,
                                ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(2, byteOrder);
        byteBuffer.put(value, startIndex, 2);
        return byteBuffer.getShort(0);
    }

    public static int toInt32(byte[] value, int startIndex, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.put(value, startIndex, 4);
        return byteBuffer.getInt(0);

    }

    public static long toInt64(byte[] value, int startIndex, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.put(value, startIndex, 8);
        return byteBuffer.getLong(0);
    }

    public static float toFloat(byte[] value, int startIndex,
                                ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.put(value, startIndex, 4);
        return byteBuffer.getFloat(0);
    }

    public static double toDouble(byte[] value, int startIndex,
                                  ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.put(value, startIndex, 8);
        return byteBuffer.getDouble(0);
    }

    public static byte[] getBytes(short value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(2, byteOrder);
        byteBuffer.putShort(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(int value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.putInt(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(long value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.putLong(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(float value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.putFloat(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(double value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.putDouble(value);
        return byteBuffer.array();
    }

    public static String toString(ByteBuffer buffer, int length) {
        byte[] value = new byte[length];
        buffer.get(value);
        return toString(value, 0, length);
    }

    public static String toString(byte[] value, int startIndex, int length) {
        try {
            return new String(value, startIndex, Math.min(value.length, length), CHARSET_UTF8).trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] reversionByte(byte[] value) {
        int length = value.length;
        byte[] resultValue = new byte[length];
        int j = 0;
        for (int i = length - 1; i >= 0; i--) {
            resultValue[j++] = value[i];
        }
        return resultValue;
    }

    /**
     * Convert byte[] to hex
     * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }

        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }

        return stringBuilder.toString().toUpperCase();
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        //start added by jjb
        int remaining = hexString.length() % 2;
        if (remaining > 0) {
            length += 1;
            hexString = "0" + hexString;
        }
        //end added by jjb
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static ByteBuffer allocate(int capacity, ByteOrder byteOrder) {
        return ByteBuffer.allocate(capacity).order(byteOrder);
    }

    /**
     * 字符串转换成压缩bcd码
     *
     * @param s
     * @return
     */
    public static byte[] str2cbcd(String s) {
        if (s.length() % 2 != 0) {
            s = "0" + s;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i += 2) {
            int high = cs[i] - 48;
            int low = cs[i + 1] - 48;
            baos.write(high << 4 | low);
        }
        return baos.toByteArray();
    }

    /**
     * 压缩bcd编码转换为字符串
     *
     * @param b
     * @return
     */
    public static String cbcd2string(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            int h = ((b[i] & 0xff) >> 4) + 48;
            sb.append((char) h);
            int l = (b[i] & 0x0f) + 48;
            sb.append((char) l);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String l = "1232.3456";
        byte[] lbyte = str2cbcd(l);
        System.out.println(cbcd2string(lbyte));

    }
}
