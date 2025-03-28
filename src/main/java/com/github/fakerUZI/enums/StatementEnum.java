package com.github.fakerUZI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 曹聪
 * @create 2025/3/1-10:02
 * @description 对账单下载账单类型枚举类
 **/
@Getter
@AllArgsConstructor
public enum StatementEnum {


    /**
     * 支付成功和转入退款的订单
     */
    ALL("ALL", "返回支付成功和转入退款的订单，默认值"),

    /**
     * 支付成功的订单
     */
    SUCCESS("SUCCESS", "返回支付成功的订单"),

    /**
     * 转入退款的订单
     */
    REFUND("REFUND", "返回转入退款的订单");

    private final String type;

    private final String description;


}
