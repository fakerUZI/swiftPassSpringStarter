package com.github.fakerUZI.result.statementDownloadSubclass;

import com.github.fakerUZI.annotation.BindName;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 曹聪
 * @create 2025/3/1-09:58
 * @description 交易记录实体类
 **/
@Data
@ToString
public class TradeRecord {

    @BindName(columnName = "交易时间")
    private LocalDateTime transactionTime;

    @BindName(columnName = "公众账号ID")
    private String appId;

    @BindName(columnName = "第三方商户号")
    private String thirdPartyMerchantId;

    @BindName(columnName = "商户号")
    private String merchantId;

    @BindName(columnName = "子商户号")
    private String subMerchantId;

    @BindName(columnName = "设备编号")
    private String deviceId;

    @BindName(columnName = "平台订单号")
    private String platformOrderNo;

    @BindName(columnName = "第三方订单号")
    private String thirdPartyOrderNo;

    @BindName(columnName = "商户订单号")
    private String merchantOrderNo;

    @BindName(columnName = "用户标识")
    private String userId;

    @BindName(columnName = "交易类型")
    private String tradeType;

    @BindName(columnName = "交易状态")
    private String tradeStatus;

    @BindName(columnName = "付款银行")
    private String paymentBank;

    @BindName(columnName = "货币种类")
    private String currency;

    @BindName(columnName = "总金额")
    private BigDecimal totalAmount;

    @BindName(columnName = "企业红包金额")
    private BigDecimal enterpriseRedPacket;

    @BindName(columnName = "平台退款单号")
    private String platformRefundNo;

    @BindName(columnName = "商户退款单号")
    private String merchantRefundNo;

    @BindName(columnName = "退款金额")
    private BigDecimal refundAmount;

    @BindName(columnName = "企业红包退款金额")
    private BigDecimal enterpriseRedPacketRefund;

    @BindName(columnName = "退款类型")
    private String refundType;

    @BindName(columnName = "退款状态")
    private String refundStatus;

    @BindName(columnName = "商品名称")
    private String productName;

    @BindName(columnName = "商户数据包")
    private String merchantData;

    @BindName(columnName = "手续费")
    private BigDecimal serviceFee;

    @BindName(columnName = "费率")
    private String feeRate;

    @BindName(columnName = "终端类型")
    private String terminalType;

    @BindName(columnName = "对账标识")
    private String reconciliationFlag;

    @BindName(columnName = "门店编号")
    private String storeId;

    @BindName(columnName = "商户名称")
    private String merchantName;

    @BindName(columnName = "收银员")
    private String cashier;

    @BindName(columnName = "子商户ID")
    private String subMerchantId2;

    @BindName(columnName = "免充值券金额")
    private BigDecimal nonRechargeCoupon;

    @BindName(columnName = "实收金额")
    private BigDecimal receivedAmount;

    @BindName(columnName = "结算金额")
    private BigDecimal settlementAmount;

    @BindName(columnName = "扩展字段2")
    private String extField2;

    @BindName(columnName = "扩展字段3")
    private String extField3;

    @BindName(columnName = "扩展字段4")
    private String extField4;
}
