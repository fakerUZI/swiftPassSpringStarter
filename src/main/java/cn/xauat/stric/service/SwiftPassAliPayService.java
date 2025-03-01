package cn.xauat.stric.service;

import cn.xauat.stric.base.BasePayResult;
import cn.xauat.stric.config.SwiftPassPayConfig;
import cn.xauat.stric.exception.PayException;
import cn.xauat.stric.request.AlipayPayRequest;
import cn.xauat.stric.result.AlipayPayResult;
import cn.xauat.stric.result.WxInitializationPayResult;
import cn.xauat.stric.result.WxMpPaymentResponse;
import cn.xauat.stric.utils.HttpUtils;
import cn.xauat.stric.utils.JsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 曹聪
 * @create 2025/2/24-17:20
 * @description 支付宝支付类
 **/
@Service
public class SwiftPassAliPayService {

    @Resource
    private SwiftPassPayConfig swiftPassPayConfig;

    /**
     * 微信初始化请求API
     * 用于小程序和公众号支付的前置条件
     *
     * @param alipayPayRequest 支付宝支付请求参数类
     */
    public AlipayPayResult initializationRequest(AlipayPayRequest alipayPayRequest) {
        try {
            alipayPayRequest.checkAndSign();
            //进行http调用
            String responseContent = HttpUtils.post(swiftPassPayConfig.getPayBaseUrl(), alipayPayRequest.toXML());
            //将请求结果进行转换
            AlipayPayResult alipayPayResult = BasePayResult.fromXML(responseContent, AlipayPayResult.class);
            //校验请求结果
            alipayPayResult.checkResult(true);
            return alipayPayResult;
        } catch (PayException e) {
            throw new RuntimeException(e);
        }
    }

}
