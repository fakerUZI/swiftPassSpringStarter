# SwiftPass 支付网关 SDK（Spring Boot Starter）

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Spring Boot 2.7](https://img.shields.io/badge/Spring%20Boot-2.7-brightgreen)](https://spring.io/projects/spring-boot)
![Java 8+](https://img.shields.io/badge/Java-8%2B-orange)

基于Spring Boot 2.7的威富通支付网关集成SDK，支持微信小程序支付和支付宝小程序支付对接（可扩展其他支付方式）

📚 官方文档：[威富通开放平台文档](https://open.swiftpass.cn/openapi)

## ✨ 特性

- 开箱即用的Spring Boot Starter
- 支持微信小程序、支付宝小程序支付
- 完整的请求/响应对象封装
- 自动签名验证与XML报文转换
- 可扩展的支付渠道支持
- 详细的代码注释与异常处理


| 技术栈       | 版本                  |
|-------------|----------------------|
| Spring Boot | 2.7                  |
| Java        | 8+                   |

## 🚀 快速开始

引入如下依赖
```xml
<dependency>
    <groupId>cn.xauat.static</groupId>
    <artifactId>swiftpass-pay</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

配置文件增加如下配置
```
swift-pass:
  # 私钥
  rsa-pri-key: 私钥
  # 公钥
  rsa-pub-key: 公钥
  # 商户id
  mch-id: 商户id
  # 下单的url
  pay-base-url: https://gspay.gsbankchina.com/pay-main/pay/gateway
  # 加密方式
  sign-type: RSA_1_256
  # 对账单下载url
  statement-bank-url: https://gspay.gsbankchina.com/payapi/gateway
  # 微信小程序id
  wx-app-id: wxaf73db954ce6cf7f
```

使用了SPI机制自动加载几个要用到的类，则使用示例如下：
详情也可查看[TestDemo](https://github.com/fakerUZI/swiftPassSpringStarter/blob/master/src/test/java/cn/xauat/stric/TestDemo.java)
```java
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

```

## 🙏 致谢
感谢 [zact](https://github.com/zacat/swiftpass-sdk) 提供的设计思路参考

## 🤝 贡献指南
欢迎通过以下方式参与项目：

提交[Issue](https://github.com/fakerUZI/swiftPassSpringStarter/issues) 报告问题或建议

发起[Pull Request](https://github.com/fakerUZI/swiftPassSpringStarter/pulls)（请遵循现有代码风格）

补充测试用例

完善文档内容

贡献前请阅读：

使用IntelliJ IDEA默认的Java代码风格

新增功能需附带文档说明

## 📞 联系我们
如有问题请提交[Issue](https://github.com/fakerUZI/swiftPassSpringStarter/issues)或邮件联系：2324948031@qq.com
