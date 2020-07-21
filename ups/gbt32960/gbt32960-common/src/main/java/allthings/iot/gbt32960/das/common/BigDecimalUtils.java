/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.gbt32960.das.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 类BigDecimalUtils.java的实现描述：TODO 类实现描述 
 * @author mingyuan.miao 2018年4月26日 上午9:29:02
 */
public class BigDecimalUtils {

    public static double add(double a, double b, int scale, RoundingMode roundingMode) {
        return new BigDecimal(a).setScale(scale, roundingMode).add(new BigDecimal(b)).doubleValue();
    }

    public static double subtract(double a, double b, int scale, RoundingMode roundingMode) {
        return new BigDecimal(a).setScale(scale, roundingMode).subtract(new BigDecimal(b)).doubleValue();
    }

    public static double multiply(double a, double b, int scale, RoundingMode roundingMode) {
        return new BigDecimal(a).setScale(scale, roundingMode).multiply(new BigDecimal(b)).doubleValue();
    }

    public static double divide(double a, double b, int scale, RoundingMode roundingMode) {
        return new BigDecimal(a).setScale(scale, roundingMode).divide(new BigDecimal(b)).doubleValue();
    }

}
