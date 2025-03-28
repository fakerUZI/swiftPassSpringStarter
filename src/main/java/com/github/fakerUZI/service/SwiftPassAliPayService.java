package com.github.fakerUZI.service;

import com.github.fakerUZI.base.BasePayResult;
import com.github.fakerUZI.config.SwiftPassPayConfig;
import com.github.fakerUZI.exception.PayException;
import com.github.fakerUZI.request.AlipayPayRequest;
import com.github.fakerUZI.result.AlipayPayResult;
import com.github.fakerUZI.utils.HttpUtils;
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
     * 支付宝初始化请求API
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
