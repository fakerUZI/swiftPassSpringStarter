package com.github.fakerUZI.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 曹聪
 * @create 2025/2/26-14:57
 * @description 金额工具类
 **/
public class MoneyUtils {

    /**
     * 将单位为元转换为单位为分.
     *
     * @param yuan 将要转换的元的数值字符串
     * @return the integer
     */
    public static Integer yuanToFen(String yuan) {
        return new BigDecimal(yuan).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue();
    }


    public static Integer yuanToFen(BigDecimal yuan) {
        return yuan.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue();
    }
}
