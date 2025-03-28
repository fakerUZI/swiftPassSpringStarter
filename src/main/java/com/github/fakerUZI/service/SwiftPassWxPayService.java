package com.github.fakerUZI.service;

import com.github.fakerUZI.base.BasePayResult;
import com.github.fakerUZI.config.SwiftPassPayConfig;
import com.github.fakerUZI.exception.PayException;
import com.github.fakerUZI.request.WxInitializationPayRequest;
import com.github.fakerUZI.result.WxInitializationPayResult;
import com.github.fakerUZI.result.WxMpPaymentResponse;
import com.github.fakerUZI.utils.HttpUtils;
import com.github.fakerUZI.utils.JsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 曹聪
 * @create 2025/2/24-17:19
 * @description 微信支付类
 **/
@Service
public class SwiftPassWxPayService {

    @Resource
    private SwiftPassPayConfig swiftPassPayConfig;

    /**
     * 微信初始化请求API
     * 用于小程序和公众号支付的前置条件
     *
     * @param wxInitializationPayRequest 微信小程序/公众号支付初始化请求参数类
     */
    public WxMpPaymentResponse initializationRequest(WxInitializationPayRequest wxInitializationPayRequest) {
        try {
            wxInitializationPayRequest.checkAndSign();
            //进行http调用
            String responseContent = HttpUtils.post(swiftPassPayConfig.getPayBaseUrl(), wxInitializationPayRequest.toXML());
            //将请求结果进行转换
            WxInitializationPayResult wxInitializationPayResult = BasePayResult.fromXML(responseContent, WxInitializationPayResult.class);
            //校验请求结果
            wxInitializationPayResult.checkResult(true);
            return JsonUtils.parseObject(wxInitializationPayResult.getPayInfo(), WxMpPaymentResponse.class);
        } catch (PayException e) {
            throw new RuntimeException(e);
        }
    }

}
