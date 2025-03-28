package com.github.fakerUZI;

import com.github.fakerUZI.enums.StatementEnum;
import com.github.fakerUZI.request.QueryOrderRequest;
import com.github.fakerUZI.request.RefundOrderRequest;
import com.github.fakerUZI.request.StatementDownloadRequest;
import com.github.fakerUZI.request.WxInitializationPayRequest;
import com.github.fakerUZI.result.RefundOrderResult;
import com.github.fakerUZI.result.StatementDownloadResult;
import com.github.fakerUZI.result.WxMpPaymentResponse;
import com.github.fakerUZI.service.SwiftPassUnifiedService;
import com.github.fakerUZI.service.SwiftPassWxPayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author 曹聪
 * @create 2025/3/2-11:06
 * @description 测试demo类
 **/
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class TestDemo {

    @Resource
    private SwiftPassWxPayService swiftPassWxPayService;

    @Resource
    private SwiftPassUnifiedService swiftPassUnifiedService;

    /**
     * 微信预账单
     */
    @Test
    public void wxInitializationPay() {
        WxInitializationPayRequest wxInitializationPayRequest = new WxInitializationPayRequest();
        wxInitializationPayRequest.setIsMinipg("1");
        wxInitializationPayRequest.setOutTradeNo("1234567890111223311");
        wxInitializationPayRequest.setBody("测试");
        wxInitializationPayRequest.setSubAppid("wxaf73db954ce6cf7f");
        wxInitializationPayRequest.setSubOpenid("oAEHG6xZ0CDo_8PShF8mNwRcszvk");
        wxInitializationPayRequest.setTotalFee(100);

        wxInitializationPayRequest.setMchCreateIp("127.0.0.1");
        wxInitializationPayRequest.setNotifyUrl("127.0.0.1");
        wxInitializationPayRequest.setNonceStr("1234567890111223311");
        wxInitializationPayRequest.setMchId("204540051457");
        WxMpPaymentResponse wxMpPaymentResponse = swiftPassWxPayService.initializationRequest(wxInitializationPayRequest);
        System.out.println(wxMpPaymentResponse);
    }

    /**
     * 对账单下载
     */
    @Test
    public void download() {
        StatementDownloadRequest statementDownloadRequest = new StatementDownloadRequest();
        statementDownloadRequest.setBillType(StatementEnum.REFUND);
        statementDownloadRequest.setBillDate(LocalDate.now().minusDays(1));
        StatementDownloadResult statementDownloadResult = swiftPassUnifiedService.statementDownload(statementDownloadRequest);
        log.info("statementDownloadResult = {}", statementDownloadResult);
    }

    /**
     * 退款
     */
    @Test
    public void refundOrder() {
        RefundOrderRequest refundOrderRequest = new RefundOrderRequest();
        refundOrderRequest.setOutTradeNo("1900101356040097792");
        refundOrderRequest.setOutRefundNo("190010135604009779211");
        refundOrderRequest.setTotalFee(14);
        refundOrderRequest.setRefundFee(14);
        refundOrderRequest.setOpUserId("oAEHG6z9RZsba0BfoK9PovT5bKk8");
//        refundOrderRequest.setOpUserId("oAEHG6zIC1MKNDDYd9eS5_9oa-AI");
        refundOrderRequest.setNonceStr("1234567890111223311");
        RefundOrderResult refundOrderResult = swiftPassUnifiedService.refundOrder(refundOrderRequest);
        log.info("refundOrderResult = {}", refundOrderResult);
    }


    @Test
    public void queryOrder() {
        QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
        queryOrderRequest.setOutTradeNo("1900091972203450368");
        System.out.println(swiftPassUnifiedService.queryOrder(queryOrderRequest));
    }
}