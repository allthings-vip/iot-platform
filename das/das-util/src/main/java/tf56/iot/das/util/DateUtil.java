package allthings.iot.das.util;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author :  sylar
 * @FileName :  DateUtil
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
public class DateUtil {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 从 buffer 的 position 读取连续7个字节,并解析成日期
     *
     * @param payload 数据区
     * @return 解析出的日期
     */
    public static Date readDate(ByteBuffer payload) {
        byte[] timeBytes = new byte[7];
        payload.get(timeBytes);
        Date time = new GregorianCalendar(100 + timeBytes[0] + 1900,
                timeBytes[1],
                timeBytes[2],
                timeBytes[3],
                timeBytes[4],
                timeBytes[5]).getTime();

        return time;
    }


    /**
     * 从 buffer 的 position 读取连续7个字节,并解析成日期
     *
     * @param payload 数据区
     * @return 解析出的日期
     */
    public static Date readDateBySix(ByteBuffer payload) {
        byte[] timeBytes = new byte[6];
        payload.get(timeBytes);
        Date time = new GregorianCalendar(100 + timeBytes[0] + 1900,
                timeBytes[1],
                timeBytes[2],
                timeBytes[3],
                timeBytes[4],
                timeBytes[5]).getTime();

        return time;
    }

    /**
     * 将毫秒数转换成7个字节的byte数组.格式: 年-月-日-时-分-秒-周日
     *
     * @param millisecond 毫秒数
     * @return
     */
    public static byte[] millisecond2Bytes(long millisecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisecond);
        byte[] res = new byte[7];
        res[0] = (byte) (c.get(Calendar.YEAR) - 2000);
        res[1] = (byte) c.get(Calendar.MONTH);
        res[2] = (byte) c.get(Calendar.DAY_OF_MONTH);
        res[3] = (byte) c.get(Calendar.HOUR_OF_DAY);
        res[4] = (byte) c.get(Calendar.MINUTE);
        res[5] = (byte) c.get(Calendar.SECOND);
        res[6] = (byte) c.get(Calendar.DAY_OF_WEEK);

        return res;
    }

    /**
     * 将毫秒数转换成7个字节的byte数组.格式: 年-月-日-时-分-秒
     *
     * @param millisecond 毫秒数
     * @return
     */
    public static byte[] millisecond2BytesSix(long millisecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisecond);
        byte[] res = new byte[6];
        res[0] = (byte) (c.get(Calendar.YEAR) - 2000);
        res[1] = (byte) c.get(Calendar.MONTH);
        res[2] = (byte) c.get(Calendar.DAY_OF_MONTH);
        res[3] = (byte) c.get(Calendar.HOUR_OF_DAY);
        res[4] = (byte) c.get(Calendar.MINUTE);
        res[5] = (byte) c.get(Calendar.SECOND);

        return res;
    }

    /**
     * 将日期字符串解析为日期类型(String to Date)
     *
     * @param dateString 不可心为null或空
     * @param format     可以为null,默认格式化"yyyy-MM-dd"
     * @return
     * @throws Exception
     */
    public static Date parse(String dateString, String format) throws Exception {

        try {
            if (format == null) {
                format = DATETIME_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateString);
        } catch (Exception ex) {
            Exception ue = new Exception("格式化日期产生异常", ex);
            throw ue;
        } catch (Throwable t) {
            throw new Exception("格式化日期产生异常", t);
        }
    }
}
