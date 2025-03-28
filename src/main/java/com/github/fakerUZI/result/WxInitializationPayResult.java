package com.github.fakerUZI.result;

import com.github.fakerUZI.base.BasePayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.w3c.dom.Document;

/**
 * @author 曹聪
 * @create 2025/2/24-19:49
 * @description 微信小程序初始化支付请求返回结果类
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
@Accessors(chain = true)
@AllArgsConstructor
public class WxInitializationPayResult extends BasePayResult {

    // 新增字段
    @XStreamAlias("version")
    private String version;

    @XStreamAlias("code")
    private String code;

    @XStreamAlias("groupno")
    private String groupno;

    @XStreamAlias("sign_agentno")
    private String signAgentno;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("token_id")
    private String tokenId;

    @XStreamAlias("pay_info")
    private String payInfo;

    // 重写 loadXml 方法，加载新增字段
    @Override
    protected void loadXml(Document d) {
        this.version = readXmlString(d, "version");
        this.code = readXmlString(d, "code");
        this.groupno = readXmlString(d, "groupno");
        this.signAgentno = readXmlString(d, "sign_agentno");
        this.deviceInfo = readXmlString(d, "device_info");
        this.tokenId = readXmlString(d, "token_id");
        this.payInfo = readXmlString(d, "pay_info");
    }
}