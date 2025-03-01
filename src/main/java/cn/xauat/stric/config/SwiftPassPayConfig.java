package cn.xauat.stric.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author 曹聪
 * @create 2025/2/24-15:46
 * @description 支付配置类
 **/
@Configuration
@Data
@ConfigurationProperties(prefix = "swift-pass")
@Slf4j
public class SwiftPassPayConfig {

    /**
     * 微信小程序appid
     */
    private String wxAppId;

    /**
     * 商户号id
     */
    private String mchId;

    /**
     * 公钥
     */
    private String rsaPubKey;

    /**
     * 私钥
     */
    private String rsaPriKey;


    /**
     * 基础功能地址
     */
    private String payBaseUrl;

    /**
     * 对账单下载地址
     */
    private String statementBankUrl;

    /**
     * 加密类型
     */
    private String signType;
    /**
     * 该字段仅用在公司内部定时任务下载订单逻辑
     * 可按需配置
     */
    private Boolean enableScheduled;

    @PostConstruct
    public void validateConfig() {
        log.info("Validating config begin");
        if (mchId == null || rsaPubKey == null || rsaPriKey == null || payBaseUrl == null) {
            throw new IllegalStateException("PayConfig 中缺少必要的配置参数，应用无法启动！");
        }
    }

}
