package allthings.iot.util.misc;

import com.google.common.base.Joiner;
import com.google.common.reflect.Reflection;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  ReflectUtils
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
public class ReflectUtils {
    /**
     * Object转化为map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            if (value == null) {
                continue;
            }
            map.put(key, value);
        }

        return map;
    }

    /**
     * 反射实例化对应的数据包类
     *
     * @param msgCode
     * @param prefixClassName
     * @param clazz
     * @return
     */
    public static <T> T getInstance(String msgCode, String prefixClassName, Class<T> clazz) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {
        String packageName = Reflection.getPackageName(clazz);
        String className = Joiner.on(".").join(packageName, prefixClassName + msgCode);
        //初始化实例
        return (T) Class.forName(className).newInstance();
    }
}
