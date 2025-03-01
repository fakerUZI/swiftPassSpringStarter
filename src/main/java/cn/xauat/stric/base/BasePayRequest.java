package cn.xauat.stric.base;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.xauat.stric.config.SwiftPassPayConfig;
import cn.xauat.stric.config.XmlConfig;
import cn.xauat.stric.constant.PayConstants;
import cn.xauat.stric.exception.PayException;
import cn.xauat.stric.exception.SdkErrorException;
import cn.xauat.stric.exception.SdkRuntimeException;
import cn.xauat.stric.utils.BeanUtils;
import cn.xauat.stric.utils.SignUtils;
import cn.xauat.stric.utils.XStreamInitializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 曹聪
 * @create 2025/2/24-15:01
 * @description 支付请求基类
 **/
@Data
@Accessors(chain = true)
public abstract class BasePayRequest implements Serializable {

    private static final long serialVersionUID = 1605054503037969461L;

    /**
     * <pre>
     * 字段名：接口类型	.
     * 变量名：service
     * 是否必填：是
     * 类型：String(32)
     * 示例值：unified.trade.native
     * 描述：接口类型
     * </pre>
     */
    @XStreamAlias("service")
    protected String service;

    /**
     * <pre>
     * 字段名：版本号	.
     * 变量名：version
     * 是否必填：是
     * 类型：String(8)
     * 示例值：2.0
     * 描述：版本号
     * </pre>
     */
    @XStreamAlias("version")
    protected String version = "2.0";

    /**
     * <pre>
     * 字段名：字符集	.
     * 变量名：charset
     * 是否必填：是
     * 类型：String(8)
     * 示例值：2.0
     * 描述：字符集
     * </pre>
     */
    @XStreamAlias("charset")
    protected String charset = "UTF-8";
    /**
     * <pre>
     * 字段名：商户号.
     * 变量名：mch_id
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1230000109
     * 描述：微信支付分配的商户号
     * </pre>
     */
    @XStreamAlias("mch_id")
    protected String mchId;
    /**
     * <pre>
     * 字段名：随机字符串.
     * 变量名：nonce_str
     * 是否必填：是
     * 类型：String(32)
     * 示例值：5K8264ILTKCH16CQ2502SI8ZNMTM67VS
     * 描述：随机字符串，不长于32位。推荐随机数生成算法
     * </pre>
     */
    @XStreamAlias("nonce_str")
    protected String nonceStr;
    /**
     * <pre>
     * 字段名：签名.
     * 变量名：sign
     * 是否必填：是
     * 类型：String(32)
     * 示例值：C380BEC2BFD727A4B6845133519F3AD6
     * 描述：签名，详见签名生成算法
     * </pre>
     */
    @XStreamAlias("sign")
    protected String sign;
    /**
     * <pre>
     * 签名类型.
     * sign_type
     * 否
     * String(32)
     * HMAC-SHA256
     * 签名类型，目前支持RSA_1_256和RSA_1_1
     * </pre>
     */
    @XStreamAlias("sign_type")
    private String signType = "RSA_1_256";

    /**
     * 检查请求参数内容，包括必填参数以及特殊约束.
     */
    private void checkFields() throws PayException {
        //check required fields
        try {
            BeanUtils.checkRequiredFields(this);
        } catch (SdkErrorException e) {
            throw new PayException(e.getLocalizedMessage(), e);
        }

        //check other parameters
        this.checkConstraints();
    }

    /**
     * 检查约束情况.
     *
     * @throws PayException the wx pay exception
     */
    protected abstract void checkConstraints() throws PayException;

    /**
     * 是否需要nonce_str
     */
    protected boolean needNonceStr() {
        return true;
    }


    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

    /**
     * To xml string.
     *
     * @return the string
     */
    public String toXML() {

        XmlConfig xmlConfig = SpringUtil.getBean(XmlConfig.class);

        if (xmlConfig.getFastMode()) {
            return toFastXml();
        }
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(this.getClass());
        return xstream.toXML(this);
    }

    /**
     * 使用快速算法组装xml
     */
    private String toFastXml() {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(xmlRootTagName());

            Map<String, String> signParams = getSignParams();
            signParams.put("sign", sign);
            for (Map.Entry<String, String> entry : signParams.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                Element elm = root.addElement(entry.getKey());
                elm.addText(entry.getValue());
            }

            return document.asXML();
        } catch (Exception e) {
            throw new SdkRuntimeException("generate xml error", e);
        }
    }

    /**
     * 返回xml结构的根节点名称
     *
     * @return 默认返回"xml", 特殊情况可以在子类中覆盖
     */
    protected String xmlRootTagName() {
        return "xml";
    }


    /**
     * 签名时，忽略的参数.
     *
     * @return the string [ ]
     */
    protected String[] getIgnoredParamsForSign() {
        return new String[0];
    }

    /**
     * 获取签名时需要的参数.
     * 注意：不含sign属性
     */
    public Map<String, String> getSignParams() {
        Map<String, String> map = new HashMap<>(8);
        map.put("service", service);
        map.put("version", version);
        map.put("charset", charset);
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("sign_type", signType);
        storeMap(map);
        return map;
    }

    /**
     * 将属性组装到一个Map中，供签名和最终发送XML时使用.
     * 这里需要将所有的属性全部保存进来，签名的时候会自动调用getIgnoredParamsForSign进行忽略，
     * 不用担心。否则最终生成的XML会缺失。
     *
     * @param map 传入的属性Map
     */
    protected abstract void storeMap(Map<String, String> map);

    /**
     * <pre>
     * 检查参数，并设置签名.
     * 1、检查参数（注意：子类实现需要检查参数的而外功能时，请在调用父类的方法前进行相应判断）
     * 2、补充系统参数，如果未传入则从配置里读取
     * 3、生成签名，并设置进去
     * </pre>
     *
     * @throws PayException the wx pay exception
     */
    public void checkAndSign() throws PayException {
        SwiftPassPayConfig config = SpringUtil.getBean(SwiftPassPayConfig.class);
        this.checkFields();


        if (StringUtils.isBlank(getMchId())) {
            this.setMchId(config.getMchId());
        }

        if (StringUtils.isNotBlank(this.getSignType())) {
            if (config.getSignType() != null && !PayConstants.SignType.ALL_SIGN_TYPES.contains(config.getSignType())) {
                throw new PayException("非法的signType配置：" + config.getSignType() + "，请检查配置！");
            }
            this.setSignType(StringUtils.trimToNull(config.getSignType()));
        } else {
            throw new PayException("非法的sign_type参数：" + this.getSignType());
        }

        if (needNonceStr() && StringUtils.isBlank(getNonceStr())) {
            this.setNonceStr(String.valueOf(System.currentTimeMillis()));
        }

        //设置签名字段的值
        this.setSign(SignUtils.createSign(this, this.getSignType(), config.getRsaPriKey(), this.getIgnoredParamsForSign()));
    }
}
