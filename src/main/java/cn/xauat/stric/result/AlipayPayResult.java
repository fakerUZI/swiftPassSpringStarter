package cn.xauat.stric.result;

import cn.hutool.extra.spring.SpringUtil;
import cn.xauat.stric.base.BasePayResult;
import cn.xauat.stric.config.SwiftPassPayConfig;
import cn.xauat.stric.exception.PayException;
import cn.xauat.stric.utils.SignUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author 曹聪
 * @create 2025/3/1-17:48
 * @description 支付宝支付请求结果返回类
 **/
@Accessors(chain = true)
@Slf4j
@Data
public class AlipayPayResult extends BasePayResult {

    private static final long serialVersionUID = -7704302925175704217L;

    /**
     * 返回状态码
     */
    @XStreamAlias("status")
    private String status;

    /**
     * 返回信息
     */
    @XStreamAlias("message")
    private String message;

    /**
     * 网关返回码
     */
    @XStreamAlias("code")
    private String code;

    /**
     * 业务结果
     */
    @XStreamAlias("result_code")
    private String resultCode;

    /**
     * 商户号
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 连锁商户号
     */
    @XStreamAlias("groupno")
    private String groupno;

    /**
     * 授权交易机构
     */
    @XStreamAlias("sign_agentno")
    private String signAgentno;

    /**
     * 设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 错误代码
     */
    @XStreamAlias("err_code")
    private String errCode;

    /**
     * 错误代码描述
     */
    @XStreamAlias("err_msg")
    private String errCodeDes;

    /**
     * 商户appid
     */
    @XStreamAlias("appid")
    private String appid;

    /**
     * 原生支付信息
     */
    @XStreamAlias("pay_info")
    private String payInfo;

    /**
     * 支付链接
     */
    @XStreamAlias("pay_url")
    private String payUrl;

    /**
     * 签名
     */
    @XStreamAlias("sign")
    private String sign;

    /**
     * 签名类型
     */
    @XStreamAlias("sign_type")
    private String signType;

    @Override
    protected void loadXml(Document d) {
        // 加载支付宝特定的返回参数
        this.status = readXmlString(d, "status");
        this.message = readXmlString(d, "message");
        this.code = readXmlString(d, "code");
        this.resultCode = readXmlString(d, "result_code");
        this.mchId = readXmlString(d, "mch_id");
        this.groupno = readXmlString(d, "groupno");
        this.signAgentno = readXmlString(d, "sign_agentno");
        this.deviceInfo = readXmlString(d, "device_info");
        this.nonceStr = readXmlString(d, "nonce_str");
        this.errCode = readXmlString(d, "err_code");
        this.errCodeDes = readXmlString(d, "err_msg");
        this.appid = readXmlString(d, "appid");
        this.payInfo = readXmlString(d, "pay_info");
        this.payUrl = readXmlString(d, "pay_url");
        this.sign = readXmlString(d, "sign");
        this.signType = readXmlString(d, "sign_type");
    }

    @Override
    public void checkResult(boolean checkSuccess) throws PayException {
        // 校验返回结果签名
        SwiftPassPayConfig swiftPassPayConfig = SpringUtil.getBean(SwiftPassPayConfig.class);
        Map<String, String> map = toMap();

        if (!SignUtils.checkSign(map, this.getSignType(), swiftPassPayConfig.getRsaPubKey())) {
            log.error("校验结果签名失败，参数：{}", map);
            throw new PayException("签名验证失败");
        }

        // 校验返回状态码和业务结果是否成功
        if (checkSuccess) {
            if (!"0".equals(this.status) || !"0".equals(this.resultCode)) {
                StringBuilder errorMsg = new StringBuilder();
                if (this.status != null) {
                    errorMsg.append("返回状态码：").append(this.status);
                }
                if (this.message != null) {
                    errorMsg.append("，返回信息：").append(this.message);
                }
                if (this.resultCode != null) {
                    errorMsg.append("，业务结果码：").append(this.resultCode);
                }
                if (this.errCode != null) {
                    errorMsg.append("，错误代码：").append(this.errCode);
                }
                if (this.errCodeDes != null) {
                    errorMsg.append("，错误描述：").append(this.errCodeDes);
                }

                log.error("支付结果校验失败：{}", errorMsg.toString());
                throw new PayException(errorMsg.toString());
            }
        }
    }
}
