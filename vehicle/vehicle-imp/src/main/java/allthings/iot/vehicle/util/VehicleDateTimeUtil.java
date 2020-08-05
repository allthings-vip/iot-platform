package allthings.iot.vehicle.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author luhao
 * @version v1.0
 * @title VehicleDateTimeUtil
 * @date 2020/4/2 18:40
 * @description
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 */
public class VehicleDateTimeUtil {
    /**
     * 获取指定某天的开始毫秒数
     *
     * @param dateTime
     * @return
     */
    public static Long getMillisDayStart(long dateTime) {
        Date date = new Date(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定某天结束的毫秒数
     *
     * @param dateTime
     * @return
     */
    public static Long getMillisDayEnd(long dateTime) {
        Date date = new Date(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
