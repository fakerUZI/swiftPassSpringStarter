package cn.xauat.stric.service;

import cn.xauat.stric.base.BasePayResult;
import cn.xauat.stric.config.SwiftPassPayConfig;
import cn.xauat.stric.exception.PayException;
import cn.xauat.stric.request.QueryOrderRequest;
import cn.xauat.stric.request.RefundOrderRequest;
import cn.xauat.stric.request.RefundQueryOrderRequest;
import cn.xauat.stric.request.WxInitializationPayRequest;
import cn.xauat.stric.result.*;
import cn.xauat.stric.utils.HttpUtils;
import cn.xauat.stric.utils.JsonUtils;
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
