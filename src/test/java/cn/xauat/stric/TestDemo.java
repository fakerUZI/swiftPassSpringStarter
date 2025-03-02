package cn.xauat.stric;

import cn.hutool.extra.servlet.ServletUtil;
import cn.xauat.stric.request.WxInitializationPayRequest;
import cn.xauat.stric.result.WxMpPaymentResponse;
import cn.xauat.stric.service.SwiftPassWxPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 曹聪
 * @create 2025/3/2-11:06
 * @description 测试demo类
 **/
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDemo {

    @Autowired
    private SwiftPassWxPayService swiftPassWxPayService;

    @Test
    public void conflictTime() {
        WxInitializationPayRequest wxInitializationPayRequest = new WxInitializationPayRequest();
        wxInitializationPayRequest.setIsMinipg("1");
        wxInitializationPayRequest.setOutTradeNo("12345678901112233");
        wxInitializationPayRequest.setBody("测试");
        wxInitializationPayRequest.setSubAppid("wxaf73db954ce6cf7f");
        wxInitializationPayRequest.setSubOpenid("oAEHG6xZ0CDo_8PShF8mNwRcszvk");
        wxInitializationPayRequest.setTotalFee(100);

        wxInitializationPayRequest.setMchCreateIp("127.0.0.1");
        wxInitializationPayRequest.setNotifyUrl("127.0.0.1");
        wxInitializationPayRequest.setNonceStr("12345678901112233");
        wxInitializationPayRequest.setMchId("204540051457");
        WxMpPaymentResponse wxMpPaymentResponse = swiftPassWxPayService.initializationRequest(wxInitializationPayRequest);
        System.out.println(wxMpPaymentResponse);
    }
}