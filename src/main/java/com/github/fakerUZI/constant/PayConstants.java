package com.github.fakerUZI.constant;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.Format;
import java.util.List;

/**
 * @author 曹聪
 * @create 2025/2/24-15:42
 * @description 威富通支付的常量类
 **/
public interface PayConstants {
    /**
     * 拉取订单评价数据接口的参数中日期格式.
     */
    Format QUERY_COMMENT_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss");


    /**
     * 签名类型.
     */
    class SignType {
        /**
         * The constant HMAC_SHA256.
         */
        public static final String RSA256 = "RSA_1_256";
        /**
         * The constant MD5.
         */
        public static final String RSA = "RSA_1_1";
        /**
         * The constant ALL_SIGN_TYPES.
         */
        public static final List<String> ALL_SIGN_TYPES = Lists.newArrayList(RSA256, RSA);
    }

    /**
     * 业务结果代码.
     */
    class ResultCode {
        /**
         * 成功.
         */
        public static final String SUCCESS = "0";

        /**
         * 失败.
         */
        public static final String FAIL = "1";
    }
}
