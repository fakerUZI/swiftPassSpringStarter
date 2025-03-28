package com.github.fakerUZI.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 曹聪
 * @create 2025/2/24-15:51
 * @description xml配置类
 **/
@Configuration
@ConfigurationProperties(prefix = "xml-config")
@Data
public class XmlConfig {

    /**
     * 是否使用快速模式
     * <p>
     * 如果设置为true，将会影响下面的方法，不再使用反射的方法来进行xml转换。
     * 1: BaseWxPayRequest的toXML方法
     * 2: BaseWxPayResult的fromXML方法
     * <p>
     * 启用快速模式后，将能：
     * 1：性能提升约 10 ~ 15倍
     * 2：可以通过 graalvm 生成native image，大大减少系统开销(CPU,RAM)，加快应用启动速度(亚秒级)，加快系统部署速度（脱离JRE）.
     * <p>
     * 参考测试案例: com.github.binarywang.wxpay.bean.result.WxPayRedpackQueryResultTest#benchmark
     * 参考网址: https://www.graalvm.org/
     */
    private Boolean fastMode = false;
}
