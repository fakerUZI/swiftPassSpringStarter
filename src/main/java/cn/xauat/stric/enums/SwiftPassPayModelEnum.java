package cn.xauat.stric.enums;

import lombok.Getter;

/**
 * @author 曹聪
 * @create 2025/2/26-15:20
 * @description 威富通支付枚举类
 **/
@Getter
public enum SwiftPassPayModelEnum {

    /**
     * 微信小程序
     */
    WECHAT_MINI_PROGRAM("WX_MP", "微信小程序"),

    /**
     * 支付宝小程序
     */
    ALI_MINI_PROGRAM("ALI_MP", "支付宝小程序"),

    /**
     * 默认支付方式
     */
    DEFAULT_MODEL("DEFAULT", "默认支付方式"),
    ;

    private final String payModel;


    private final String description;


    SwiftPassPayModelEnum(String payModel, String description) {
        this.payModel = payModel;
        this.description = description;
    }

    public static SwiftPassPayModelEnum getByPayModel(String payModel) {
        for (SwiftPassPayModelEnum payModelEnum : SwiftPassPayModelEnum.values()) {
            if (payModelEnum.getPayModel().equals(payModel)) {
                return payModelEnum;
            }
        }
        return DEFAULT_MODEL;
    }
}
