package com.github.fakerUZI.base;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.github.fakerUZI.config.SwiftPassPayConfig;
import com.github.fakerUZI.config.XmlConfig;
import com.github.fakerUZI.constant.PayConstants;
import com.github.fakerUZI.exception.PayException;
import com.github.fakerUZI.exception.SdkRuntimeException;
import com.github.fakerUZI.utils.SignUtils;
import com.github.fakerUZI.utils.XStreamInitializer;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * @author 曹聪
 * @create 2025/2/24-15:19
 * @description 支付返回结果基类
 **/
@Accessors(chain = true)
@Slf4j
@Data
public abstract class BasePayResult implements Serializable {

    private static final long serialVersionUID = -7704302925175704217L;

    /**
     * 字符集
     */
    @XStreamAlias("charset")
    protected String charset;

    /**
     * 返回状态码.
     */
    @XStreamAlias("status")
    protected String returnCode;
    /**
     * 返回信息.
     */
    @XStreamAlias("message")
    protected String returnMsg;

    //当return_code为SUCCESS的时候，还会包括以下字段：

    /**
     * 业务结果.
     */
    @XStreamAlias("result_code")
    private String resultCode;
    /**
     * 错误代码.
     */
    @XStreamAlias("err_code")
    private String errCode;

    /**
     * 错误代码描述.
     */
    @XStreamAlias("err_msg")
    private String errCodeDes;
    /**
     * 公众账号ID.
     */
    @XStreamAlias("appid")
    private String appid;
    /**
     * 商户号.
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 随机字符串.
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;
    /**
     * 签名.
     */
    @XStreamAlias("sign")
    private String sign;

    /**
     * 签名.
     */
    @XStreamAlias("sign_type")
    private String signType;

    //以下为辅助属性
    /**
     * xml字符串.
     */
    private String xmlString;

    /**
     * xml的Document对象，用于解析xml文本.
     * make xmlDoc transient to ensure toString() can work.
     */
    private transient Document xmlDoc;

    /**
     * 将单位分转换成单位圆.
     *
     * @param fen 将要被转换为元的分的数值
     * @return the string
     */
    public static String fenToYuan(Integer fen) {
        return BigDecimal.valueOf(Double.valueOf(fen) / 100).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 从xml字符串创建bean对象.
     *
     * @param <T>       the type parameter
     * @param xmlString the xml string
     * @param clz       the clz
     * @return the t
     */
    public static <T extends BasePayResult> T fromXML(String xmlString, Class<T> clz) {
        XmlConfig xmlConfig = SpringUtil.getBean(XmlConfig.class);
        if (xmlConfig.getFastMode()) {
            try {
                BasePayResult t = clz.newInstance();
                t.setXmlString(xmlString);
                Document doc = t.getXmlDoc();
                t.loadBasicXML(doc);
                t.loadXml(doc);
                return (T) t;
            } catch (Exception e) {
                throw new SdkRuntimeException("parse xml error", e);
            }
        }
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(clz);
        xstream.setClassLoader(BasePayResult.class.getClassLoader());
        T result = (T) xstream.fromXML(xmlString);
        result.setXmlString(xmlString);
        return result;
    }

    protected static Boolean readXmlBoolean(Node d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().isEmpty()) {
            return null;
        }
        return Boolean.parseBoolean(content);
    }

    protected static Integer readXmlInteger(Node d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(content);
    }

    protected static String readXmlString(Node d, String tagName) {
        if (!d.hasChildNodes()) {
            return null;
        }
        NodeList childNodes = d.getChildNodes();
        for (int i = 0, j = childNodes.getLength(); i < j; i++) {
            Node node = childNodes.item(i);
            if (tagName.equals(node.getNodeName())) {
                if (!node.hasChildNodes()) {
                    return null;
                }
                return node.getFirstChild().getNodeValue();
            }
        }
        return null;
    }

    public static String readXmlString(Document d, String tagName) {
        NodeList elements = d.getElementsByTagName(tagName);
        if (elements == null || elements.getLength() == 0) {
            return null;
        }

        Node node = elements.item(0).getFirstChild();
        if (node == null) {
            return null;
        }
        return node.getNodeValue();
    }

    protected static Integer readXmlInteger(Document d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().isEmpty()) {
            return null;
        }

        return Integer.parseInt(content);
    }

    protected static Boolean readXmlBoolean(Document d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().isEmpty()) {
            return null;
        }

        return Boolean.parseBoolean(content);
    }

    protected static Long readXmlLong(Document d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().isEmpty()) {
            return null;
        }

        return Long.parseLong(content);
    }

    /**
     * 从XML文档中加载属性,供子类覆盖加载额外的属性
     *
     * @param d Document
     */
    protected abstract void loadXml(Document d);

    /**
     * 从XML文档中加载基础属性
     *
     * @param d Document
     */
    private void loadBasicXML(Document d) {
        returnCode = readXmlString(d, "status");
        returnMsg = readXmlString(d, "message");
        resultCode = readXmlString(d, "result_code");
        errCode = readXmlString(d, "err_code");
        errCodeDes = readXmlString(d, "err_msg");
        appid = readXmlString(d, "appid");
        mchId = readXmlString(d, "mch_id");
        nonceStr = readXmlString(d, "nonce_str");
        sign = readXmlString(d, "sign");
        signType = readXmlString(d, "sign_type");
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

    /**
     * 将bean通过保存的xml字符串转换成map.
     *
     * @return the map
     */
    public Map<String, String> toMap() {
        if (StringUtils.isBlank(this.xmlString)) {
            throw new SdkRuntimeException("xml数据有问题，请核实！");
        }

        Map<String, String> result = Maps.newHashMap();
        Document doc = this.getXmlDoc();

        try {
            NodeList list = (NodeList) XPathFactory.newInstance().newXPath().compile("/xml/*").evaluate(doc, XPathConstants.NODESET);
            int len = list.getLength();
            for (int i = 0; i < len; i++) {
                result.put(list.item(i).getNodeName(), list.item(i).getTextContent());
            }
        } catch (XPathExpressionException e) {
            throw new SdkRuntimeException("非法的xml文本内容：" + xmlString);
        }

        return result;
    }

    /**
     * 将xml字符串转换成Document对象，以便读取其元素值.
     */
    private Document getXmlDoc() {
        if (this.xmlDoc != null) {
            return this.xmlDoc;
        }
        xmlDoc = openXML(xmlString);
        return xmlDoc;
    }

    protected Document openXML(String content) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            return factory.newDocumentBuilder().parse(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new SdkRuntimeException("非法的xml文本内容：\n" + this.xmlString, e);
        }
    }

    /**
     * 获取xml中元素的值.
     *
     * @param path the path
     * @return the xml value
     */
    protected String getXmlValue(String... path) {
        Document doc = this.getXmlDoc();
        String expression = String.format("/%s//text()", Joiner.on("/").join(path));
        try {
            return (String) XPathFactory.newInstance().newXPath().compile(expression).evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new SdkRuntimeException("未找到相应路径的文本：" + expression);
        }
    }

    /**
     * 获取xml中元素的值，作为int值返回.
     *
     * @param path the path
     * @return the xml value as int
     */
    protected Integer getXmlValueAsInt(String... path) {
        String result = this.getXmlValue(path);
        if (StringUtils.isBlank(result)) {
            return null;
        }

        return Integer.valueOf(result);
    }

    /**
     * 校验返回结果签名.
     *
     * @param checkSuccess 是否同时检查结果是否成功
     * @throws PayException the wx pay exception
     */
    public void checkResult(boolean checkSuccess) throws PayException {

        SwiftPassPayConfig swiftPassPayConfig = SpringUtil.getBean(SwiftPassPayConfig.class);

        //校验返回结果签名
        Map<String, String> map = toMap();
        if (getSign() != null && !SignUtils.checkSign(map, this.getSignType(), swiftPassPayConfig.getRsaPubKey())) {
            log.error("校验结果签名失败，参数：{}", map);
            throw new PayException("参数格式校验错误！");
        }

        //校验结果是否成功
        if (checkSuccess) {
            List<String> successStrings = Lists.newArrayList(PayConstants.ResultCode.SUCCESS, "");
            if (!successStrings.contains(StringUtils.trimToEmpty(getReturnCode()).toUpperCase()) || !successStrings.contains(StringUtils.trimToEmpty(getResultCode()).toUpperCase())) {
                StringBuilder errorMsg = new StringBuilder();
                if (getReturnCode() != null) {
                    errorMsg.append("返回代码：").append(getReturnCode());
                }
                if (getReturnMsg() != null) {
                    errorMsg.append("，返回信息：").append(getReturnMsg());
                }
                if (getResultCode() != null) {
                    errorMsg.append("，结果代码：").append(getResultCode());
                }
                if (getErrCode() != null) {
                    errorMsg.append("，错误代码：").append(getErrCode());
                }
                if (getErrCodeDes() != null) {
                    errorMsg.append("，错误详情：").append(getErrCodeDes());
                }

                log.error("结果业务代码异常，返回结果：{},\n{}", map, errorMsg);
                throw PayException.from(this);
            }
        }
    }
}
