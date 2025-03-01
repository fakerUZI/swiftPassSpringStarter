package cn.xauat.stric.request;

import cn.xauat.stric.annotation.Required;
import cn.xauat.stric.base.BasePayRequest;
import cn.xauat.stric.exception.PayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class RefundQueryOrderRequest extends BasePayRequest {

    @XStreamAlias("out_trade_no")
    String outTradeNo;


    @XStreamAlias("transaction_id")
    String transactionId;

    @Required
    @XStreamAlias("out_refund_no")
    String outRefundNo;

    @XStreamAlias("refund_id")
    String refundId;

    @Override
    public void checkAndSign() throws PayException {
        this.service = "unified.trade.refundquery";
        super.checkAndSign();
    }

    @Override
    protected void checkConstraints() throws PayException {
        if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)
                && StringUtils.isBlank(outRefundNo) && StringUtils.isBlank(refundId)) ||
                (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo)
                        && StringUtils.isNotBlank(outRefundNo) && StringUtils.isNotBlank(refundId))) {
            throw new PayException("transactionId，outRefundNo，transactionId，refundId 必须四选一");
        }
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("transaction_id", transactionId);
        map.put("out_trade_no", outTradeNo);
        map.put("out_refund_no", outRefundNo);
        map.put("refund_id", refundId);
    }
}
