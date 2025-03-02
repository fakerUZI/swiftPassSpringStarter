package cn.xauat.stric.request;

import cn.hutool.extra.spring.SpringUtil;
import cn.xauat.stric.annotation.Required;
import cn.xauat.stric.base.BasePayRequest;
import cn.xauat.stric.config.SwiftPassPayConfig;
import cn.xauat.stric.constant.PayConstants;
import cn.xauat.stric.exception.PayException;
import cn.xauat.stric.utils.SignUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author 曹聪
 * @create 2025/2/24-17:57
 * @description 微信初始化请求对象类
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class WxInitializationPayRequest extends BasePayRequest {

    /**
     * <pre>
     * 字段名：APPID
     * 变量名：appid
     * 是否必填：否
     * 类型：String(32)
     * 描述：服务商公众号APPID
     * </pre>
     */
    @XStreamAlias("appid")
    private String appid;

    /**
     * <pre>
     * 字段名：授权交易机构
     * 变量名：sign_agentno
     * 是否必填：否
     * 类型：String(32)
     * 示例值：XYZ123
     * 描述：授权交易机构编号/服务商编号，由平台分配。商户授权交易给机构/服务商后，由机构/服务商给商户发交易时必填，签名使用机构/服务商的密钥
     * </pre>
     */
    @XStreamAlias("sign_agentno")
    private String signAgentno;

    /**
     * <pre>
     * 字段名：商户名称
     * 变量名：merchant_name
     * 是否必填：否
     * 类型：String(128)
     * 示例值：My Merchant Name
     * 描述：商户入网的主体名称
     * </pre>
     */
    @XStreamAlias("merchant_name")
    private String merchantName;

    /**
     * <pre>
     * 字段名：原生JS
     * 变量名：is_raw
     * 是否必填：是
     * 类型：String(1)
     * 示例值：1
     * 描述：值为1
     * </pre>
     */
    @XStreamAlias("is_raw")
    @Required
    private String isRaw = "1";

    /**
     * <pre>
     * 字段名：是否小程序支付
     * 变量名：is_minipg
     * 是否必填：否
     * 类型：String(1)
     * 示例值：1
     * 描述：值为1，表示小程序支付；不传或值不为1，表示公众账号内支付
     * </pre>
     */
    @XStreamAlias("is_minipg")
    private String isMinipg;

    /**
     * <pre>
     * 字段名：商户订单号
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：2024090112345678
     * 描述：商户系统内部的订单号 ,5到32个字符、 只能包含字母数字或者下划线，区分大小写，每次下单请求确保在商户系统唯一
     * </pre>
     */
    @XStreamAlias("out_trade_no")
    @Required
    private String outTradeNo;

    /**
     * <pre>
     * 字段名：设备号
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：POS1234
     * 描述：终端设备号
     * </pre>
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * <pre>
     * 字段名：操作员
     * 变量名：op_user_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：op123
     * 描述：操作员帐号,默认为商户号
     * </pre>
     */
    @XStreamAlias("op_user_id")
    private String opUserId;

    /**
     * <pre>
     * 字段名：门店编号
     * 变量名：op_shop_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：shop123
     * 描述：门店编号
     * </pre>
     */
    @XStreamAlias("op_shop_id")
    private String opShopId;

    /**
     * <pre>
     * 字段名：商品描述
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值：商品描述
     * 描述：商品描述
     * </pre>
     */
    @XStreamAlias("body")
    @Required
    private String body;

    /**
     * <pre>
     * 字段名：单品信息
     * 变量名：goods_detail
     * 是否必填：否
     * 类型：String (6000)
     * 示例值：{"goods_detail": [{"goods_id": "123","quantity": 1,"price": 100}]}
     * 描述：单品优惠活动该字段必传，且必须按照规范上传，JSON格式，详见【优惠活动字段说明】
     * </pre>
     */
    @XStreamAlias("goods_detail")
    private String goodsDetail;

    /**
     * <pre>
     * 字段名：用户openid
     * 变量名：sub_openid
     * 是否必填：是
     * 类型：String(128)
     * 示例值：oScRj5VO8LXmOl0nIIXA
     * 描述：微信用户关注商家公众号的openid（注：使用测试号时此参数置空，即不要传这个参数，使用正式商户号时才传入，参数名是sub_openid，具体请看文档最后注意事项第7点）
     * </pre>
     */
    @XStreamAlias("sub_openid")
    @Required
    private String subOpenid;

    /**
     * <pre>
     * 字段名：公众账号或小程序ID
     * 变量名：sub_appid
     * 是否必填：是
     * 类型：String(32)
     * 示例值：wx1234567890abcdef
     * 描述：当发起公众号支付时，值是微信公众平台基本配置中的AppID(应用ID)；当发起小程序支付时，值是对应小程序的AppID
     * </pre>
     */
    @XStreamAlias("sub_appid")
    @Required
    private String subAppid;

    /**
     * <pre>
     * 字段名：附加信息
     * 变量名：attach
     * 是否必填：否
     * 类型：String(127)
     * 示例值：附加信息
     * 描述：附加数据，支付成功后在查询API和支付通知API中原样返回，该字段主要用于商户携带订单的自定义数据
     * </pre>
     */
    @XStreamAlias("attach")
    private String attach;

    /**
     * <pre>
     * 字段名：总金额
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：总金额，以分为单位，不允许包含任何字、符号
     * </pre>
     */
    @XStreamAlias("total_fee")
    @Required
    private Integer totalFee;

    /**
     * <pre>
     * 字段名：电子发票
     * 变量名：need_receipt
     * 是否必填：否
     * 类型：boolean
     * 示例值：true
     * 描述：需要和微信公众平台的发票功能联合，传入true时，微信支付成功消息和支付详情页将出现开票入口[新增need_receipt【适用于微信】]
     * </pre>
     */
    @XStreamAlias("need_receipt")
    private Boolean needReceipt;

    /**
     * <pre>
     * 字段名：终端IP
     * 变量名：mch_create_ip
     * 是否必填：是
     * 类型：String(16)
     * 示例值：192.168.1.1
     * 描述：上传商户真实的发起交易的终端出网IP
     * </pre>
     */
    @XStreamAlias("mch_create_ip")
    @Required
    private String mchCreateIp;

    /**
     * <pre>
     * 字段名：经纬度
     * 变量名：device_location
     * 是否必填：否
     * 类型：String(32)
     * 示例值：+37.123456789/-121.123456789
     * 描述：格式：纬度/经度，+表示北纬、东经，-表示南纬、 西经，精度最长支持小数点后9位
     * </pre>
     */
    @XStreamAlias("device_location")
    private String deviceLocation;

    /**
     * <pre>
     * 字段名：通知地址
     * 变量名：notify_url
     * 是否必填：是
     * 类型：String(255)
     * 示例值：http://www.example.com/notify
     * 描述：接收平台异步回调通知的地址，必须为外网可访问的url，确保平台能通过互联网访问该地址，若无需接收通知，请固定传值为127.0.0.1
     * </pre>
     */
    @XStreamAlias("notify_url")
    @Required
    private String notifyUrl;

    /**
     * <pre>
     * 字段名：订单生成时间
     * 变量名：time_start
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20240923123456
     * 描述：订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器。注：订单生成时间与超时时间需要同时传入才会生效
     * </pre>
     */
    @XStreamAlias("time_start")
    private String timeStart;

    /**
     * <pre>
     * 字段名：订单超时时间
     * 变量名：time_expire
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20240923235959
     * 描述：订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。时区为GMT+8 beijing。该时间取自商户服务器。注：订单生成时间与超时时间需要同时传入才会生效
     * </pre>
     */
    @XStreamAlias("time_expire")
    private String timeExpire;

    /**
     * <pre>
     * 字段名：商品标记
     * 变量名：goods_tag
     * 是否必填：否
     * 类型：String(32)
     * 示例值：tag1
     * 描述：商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
     * </pre>
     */
    @XStreamAlias("goods_tag")
    private String goodsTag;

    /**
     * <pre>
     * 字段名：是否限制信用卡
     * 变量名：limit_credit_pay
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1
     * 描述：限定用户使用时能否使用信用卡，值为1，禁用信用卡；值为0或者不传此参数则不禁用
     * </pre>
     */
    @XStreamAlias("limit_credit_pay")
    private String limitCreditPay;

    /**
     * <pre>
     * 字段名：受理终端信息
     * 变量名：terminal_info
     * 是否必填：否
     * 类型：String(1024)
     * 示例值：{"location": "+37.123456789/-121.123456789", "network_license": "P3100", "terminal_type": "02", "terminal_id": "12345678", "app_version": "1.0"}
     * 描述：商户侧受理终端信息，格式为 json 格式，详见下文子字段说明
     * </pre>
     */
    @XStreamAlias("terminal_info")
    private String terminalInfo;

    /**
     * <pre>
     * 字段名：连锁商户号
     * 变量名：groupno
     * 是否必填：否
     * 类型：String(32)
     * 连锁商户编号，由平台分配。连锁商户为其下门店发交易的情况返回
     * </pre>
     */
    @XStreamAlias("groupno")
    private String groupno;


    @Override
    protected void checkConstraints() throws PayException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {

    }

    public void checkAndSign() throws PayException {
        this.service = "pay.weixin.jspay";
        SwiftPassPayConfig bean = SpringUtil.getBean(SwiftPassPayConfig.class);
        this.mchId = bean.getMchId();
        super.checkAndSign();
    }
}