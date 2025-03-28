package com.github.fakerUZI.request;

import com.github.fakerUZI.base.BasePayRequest;
import com.github.fakerUZI.exception.PayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author 曹聪
 * @create 2025/3/1-17:40
 * @description 支付宝支付请求类
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AlipayPayRequest extends BasePayRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 字段名：连锁商户号.
     * 变量名：groupno
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234567890
     * 描述：连锁商户编号，由平台分配。连锁商户为其下门店发交易的情况必填，签名使用连锁商户的密钥
     */
    @XStreamAlias("groupno")
    private String groupno;

    /**
     * 字段名：授权交易机构.
     * 变量名：sign_agentno
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234567890
     * 描述：授权交易机构编号/服务商编号，由平台分配。商户授权交易给机构/服务商后，由机构/服务商给商户发交易时必填，签名使用机构/服务商的密钥
     */
    @XStreamAlias("sign_agentno")
    private String signAgentno;

    /**
     * 字段名：商户名称.
     * 变量名：merchant_name
     * 是否必填：否
     * 类型：String(128)
     * 示例值：测试商户
     * 描述：商户入网的主体名称
     */
    @XStreamAlias("merchant_name")
    private String merchantName;

    /**
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：202403010001
     * 描述：商户系统内部的订单号,5到32个字符、只能包含字母数字或者下划线，区分大小写，每次下单请求确保在商户系统唯一
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：013465789
     * 描述：终端设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：操作员.
     * 变量名：op_user_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234567890
     * 描述：操作员帐号，默认为商户号
     */
    @XStreamAlias("op_user_id")
    private String opUserId;

    /**
     * 字段名：门店编号.
     * 变量名：op_shop_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234567890
     * 描述：商家自定义门店id，对应支付宝侧 store_id字段，有参加支付宝加油活动则必传，为支付宝加油产品的外部油站 id
     */
    @XStreamAlias("op_shop_id")
    private String opShopId;

    /**
     * 字段名：商品描述.
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值：测试商品
     * 描述：商品描述
     */
    @XStreamAlias("body")
    private String body;

    /**
     * 字段名：支付宝加油信息.
     * 变量名：thi_extend_params
     * 是否必填：否
     * 类型：String(1024)
     * 示例值：{"key1":"value1","key2":"value2"}
     * 描述：参加支付宝加油活动产品必传，且必须按照规范上传，JSON格式，详见【支付宝加油活动字段说明】
     */
    @XStreamAlias("thi_extend_params")
    private String thiExtendParams;

    /**
     * 字段名：单品信息.
     * 变量名：goods_detail
     * 是否必填：否
     * 类型：String(6000)
     * 示例值：[{"goods_id":"12345","name":"测试商品","quantity":1,"price":100}]
     * 描述：单品优惠活动该字段必传，且必须按照规范上传，JSON格式，详见【优惠活动字段说明】
     */
    @XStreamAlias("goods_detail")
    private String goodsDetail;

    /**
     * 字段名：附加信息.
     * 变量名：attach
     * 是否必填：否
     * 类型：String(127)
     * 示例值：附加信息
     * 描述：附加数据，支付成功后在查询API和支付通知API中原样返回，该字段主要用于商户携带订单的自定义数据。另外在业务允许的特殊场景下作为分账字段使用，详情请查看分账产品介绍文档
     */
    @XStreamAlias("attach")
    private String attach;

    /**
     * 字段名：总金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：总金额，以分为单位，不允许包含任何字、符号
     */
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 字段名：花呗分期数.
     * 变量名：hb_fq_num
     * 是否必填：否
     * 类型：String
     * 示例值：3
     * 描述：只支持传"3"|"6"|"12"，只适用于支付宝支付
     */
    @XStreamAlias("hb_fq_num")
    private String hbFqNum;

    /**
     * 字段名：承担手续费角色.
     * 变量名：hb_fq_seller_percent
     * 是否必填：否
     * 类型：String
     * 示例值：100
     * 描述：只支持传"0"|"100"，商家承担手续费传"100"，用户承担传"0"，在有hb_fq_num字段时默认为“0”
     */
    @XStreamAlias("hb_fq_seller_percent")
    private String hbFqSellerPercent;

    /**
     * 字段名：支付宝可打折金额.
     * 变量名：discountable_amount
     * 是否必填：否
     * 类型：Int
     * 示例值：100
     * 描述：单位/分
     */
    @XStreamAlias("discountable_amount")
    private Integer discountableAmount;

    /**
     * 字段名：终端IP.
     * 变量名：mch_create_ip
     * 是否必填：是
     * 类型：String(16)
     * 示例值：192.168.1.1
     * 描述：上传商户真实的发起交易的终端出网IP
     */
    @XStreamAlias("mch_create_ip")
    private String mchCreateIp;

    /**
     * 字段名：经纬度.
     * 变量名：device_location
     * 是否必填：否
     * 类型：String(32)
     * 示例值：+37.123456789/-121.123456789
     * 描述：格式：纬度/经度，+表示北纬、东经，-表示南纬、西经，精度最长支持小数点后9位
     */
    @XStreamAlias("device_location")
    private String deviceLocation;

    /**
     * 字段名：通知地址.
     * 变量名：notify_url
     * 是否必填：是
     * 类型：String(255)
     * 示例值：http://example.com/notify
     * 描述：接收平台异步回调通知的地址，必须为外网可访问的url，确保平台能通过互联网访问该地址，若无需接收通知，请固定传值为127.0.0.1
     */
    @XStreamAlias("notify_url")
    private String notifyUrl;

    /**
     * 字段名：限制信用卡.
     * 变量名：limit_credit_pay
     * 是否必填：否
     * 类型：String(1)
     * 示例值：1
     * 描述：限定用户使用时能否使用信用卡，值为1，禁用信用卡，值为0或者不传此参数则不禁用
     */
    @XStreamAlias("limit_credit_pay")
    private String limitCreditPay;

    /**
     * 字段名：订单生成时间.
     * 变量名：time_start
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20240301091010
     * 描述：订单生成时间，格式为yyyyMMddHHmmss，时区为GMT+8beijing
     */
    @XStreamAlias("time_start")
    private String timeStart;

    /**
     * 字段名：订单超时时间.
     * 变量名：time_expire
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20240301101010
     * 描述：订单失效时间，格式为yyyyMMddHHmmss，时区为GMT+8beijing
     */
    @XStreamAlias("time_expire")
    private String timeExpire;

    /**
     * 字段名：订单最晚付款时间.
     * 变量名：qr_code_timeout_express
     * 是否必填：否
     * 类型：String(6)
     * 示例值：10m
     * 描述：该笔订单允许的最晚付款时间，逾期将关闭交易，从下单开始计时。取值范围：1m~15d。m-分钟，h-小时，d-天，1c-当天
     */
    @XStreamAlias("qr_code_timeout_express")
    private String qrCodeTimeoutExpress;

    /**
     * 字段名：商品标记.
     * 变量名：goods_tag
     * 是否必填：否
     * 类型：String(32)
     * 示例值：测试商品标记
     * 描述：商品标记，用于优惠券或者满减使用
     */
    @XStreamAlias("goods_tag")
    private String goodsTag;

    /**
     * 字段名：商品ID.
     * 变量名：product_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234567890
     * 描述：预留字段，此ID为静态可打印的二维码包含的商品ID，商户自行维护
     */
    @XStreamAlias("product_id")
    private String productId;

    /**
     * 字段名：买家支付宝账号.
     * 变量名：buyer_logon_id
     * 是否必填：否
     * 类型：String(100)
     * 示例值：buyer@alipay.com
     * 描述：买家支付宝账号，和buyer_id不能同时为空
     */
    @XStreamAlias("buyer_logon_id")
    private String buyerLogonId;

    /**
     * 字段名：买家支付宝用户ID.
     * 变量名：buyer_id
     * 是否必填：否
     * 类型：String(100)
     * 示例值：2088102146799168
     * 描述：买家支付宝用户ID，和buyer_logon_id不能同时为空
     */
    @XStreamAlias("buyer_id")
    private String buyerId;

    /**
     * 字段名：受理终端信息.
     * 变量名：terminal_info
     * 是否必填：否
     * 类型：String(1024)
     * 示例值：{"location":"+37.12/-121.213","network_license":"P3100","terminal_type":"02","terminal_id":"12345678","serial_num":"1234567890","encrypt_rand_num":"123456","secret_text":"base64加密后的密文","app_version":"1.0","terminal_ip":"192.168.1.1","mobile_country_cd":"460","mobile_net_num":"01","icc_id":"1234567890123456","location_cd1":"1234","lbs_num1":"12345678","lbs_signal1":"1234","location_cd2":"1234","lbs_num2":"12345678","lbs_signal2":"1234","location_cd3":"1234","lbs_num3":"12345678","lbs_signal3":"1234","telecom_sys_id":"1234","telecom_net_id":"1234","telecom_lbs":"1234","telecom_lbs_signal":"1234"}
     * 描述：商户侧受理终端信息，格式为json格式，详见下文子字段说明
     */
    @XStreamAlias("terminal_info")
    private String terminalInfo;

    @Override
    protected void checkConstraints() throws PayException {
        // 在这里添加对特定字段的约束检查逻辑
        // 例如：
        if (StringUtils.isNotBlank(buyerLogonId) && StringUtils.isNotBlank(buyerId)) {
            throw new PayException("buyer_logon_id和buyer_id不能同时存在");
        }
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("groupno", groupno);
        map.put("sign_agentno", signAgentno);
        map.put("merchant_name", merchantName);
        map.put("out_trade_no", outTradeNo);
        map.put("device_info", deviceInfo);
        map.put("op_user_id", opUserId);
        map.put("op_shop_id", opShopId);
        map.put("body", body);
        map.put("thi_extend_params", thiExtendParams);
        map.put("goods_detail", goodsDetail);
        map.put("attach", attach);
        map.put("total_fee", totalFee.toString());
        map.put("hb_fq_num", hbFqNum);
        map.put("hb_fq_seller_percent", hbFqSellerPercent);
        map.put("discountable_amount", discountableAmount.toString());
        map.put("mch_create_ip", mchCreateIp);
        map.put("device_location", deviceLocation);
        map.put("notify_url", notifyUrl);
        map.put("limit_credit_pay", limitCreditPay);
        map.put("time_start", timeStart);
        map.put("time_expire", timeExpire);
        map.put("qr_code_timeout_express", qrCodeTimeoutExpress);
        map.put("goods_tag", goodsTag);
        map.put("product_id", productId);
        map.put("buyer_logon_id", buyerLogonId);
        map.put("buyer_id", buyerId);
        map.put("terminal_info", terminalInfo);
    }

    @Override
    public void checkAndSign() throws PayException {
        this.service = "pay.alipay.jspay";
        super.checkAndSign();
    }
}
