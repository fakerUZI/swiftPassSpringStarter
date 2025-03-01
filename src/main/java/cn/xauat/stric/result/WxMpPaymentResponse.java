package cn.xauat.stric.result;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 曹聪
 * @create 2025/2/26-16:00
 * @description 微信小程序支付请求转换类
 **/
@Data
public class WxMpPaymentResponse {
    @JsonProperty("timeStamp")
    private String timeStamp;

    @JsonProperty("package")
    private String packageValue;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("signType")
    private String signType;

    @JsonProperty("nonceStr")
    private String nonceStr;

    @JsonProperty("status")
    private String status;

    @JsonProperty("paySign")
    private String paySign;

    @JsonProperty("callback_url")
    private String callbackUrl;
}
