package cn.xauat.stric;

import cn.xauat.stric.enums.StatementEnum;
import cn.xauat.stric.request.RefundOrderRequest;
import cn.xauat.stric.request.StatementDownloadRequest;
import cn.xauat.stric.request.WxInitializationPayRequest;
import cn.xauat.stric.result.RefundOrderResult;
import cn.xauat.stric.result.StatementDownloadResult;
import cn.xauat.stric.result.WxMpPaymentResponse;
import cn.xauat.stric.service.SwiftPassUnifiedService;
import cn.xauat.stric.service.SwiftPassWxPayService;
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
        statementDownloadRequest.setBillType(StatementEnum.ALL);
        statementDownloadRequest.setBillDate(LocalDate.now().minusDays(3));
        StatementDownloadResult statementDownloadResult = swiftPassUnifiedService.statementDownload(statementDownloadRequest);
        log.info("statementDownloadResult = {}", statementDownloadResult);
    }

    /**
     * 退款
     */
    @Test
    public void refundOrder() {
        RefundOrderRequest refundOrderRequest = new RefundOrderRequest();
        refundOrderRequest.setOutTradeNo("1894926725968695296");
        refundOrderRequest.setOutRefundNo("189492672596869529611");
        refundOrderRequest.setTotalFee(200);
        refundOrderRequest.setRefundFee(200);
        refundOrderRequest.setOpUserId("oAEHG6xZ0CDo_8PShF8mNwRcszvk");
        refundOrderRequest.setNonceStr("1234567890111223311");
        RefundOrderResult refundOrderResult = swiftPassUnifiedService.refundOrder(refundOrderRequest);
        log.info("refundOrderResult = {}", refundOrderResult);
    }
}