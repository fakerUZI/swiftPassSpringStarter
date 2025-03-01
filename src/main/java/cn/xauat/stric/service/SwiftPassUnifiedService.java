package cn.xauat.stric.service;

import cn.xauat.stric.base.BasePayResult;
import cn.xauat.stric.config.SwiftPassPayConfig;
import cn.xauat.stric.exception.PayException;
import cn.xauat.stric.request.QueryOrderRequest;
import cn.xauat.stric.request.RefundOrderRequest;
import cn.xauat.stric.request.RefundQueryOrderRequest;
import cn.xauat.stric.request.StatementDownloadRequest;
import cn.xauat.stric.result.QueryOrderResult;
import cn.xauat.stric.result.RefundOrderResult;
import cn.xauat.stric.result.RefundQueryOrderResult;
import cn.xauat.stric.result.StatementDownloadResult;
import cn.xauat.stric.utils.HttpUtils;
import cn.xauat.stric.utils.StringToMapConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 曹聪
 * @create 2025/3/1-10:32
 * @description 统一支付服务
 * 包含退款/退款查询/对账单等
 **/
@Service
public class SwiftPassUnifiedService {

    @Resource
    private SwiftPassPayConfig swiftPassPayConfig;

    /**
     * 统一对账单下载接口
     *
     * @param statementDownloadRequest 对账单下载请求参数
     * @return 统一对账单
     */
    public StatementDownloadResult statementDownload(StatementDownloadRequest statementDownloadRequest) {
        try {
            statementDownloadRequest.checkAndSign();
            //进行http调用
            String responseContent = HttpUtils.post(swiftPassPayConfig.getStatementBankUrl(), statementDownloadRequest.toXML());
            //将请求结果进行转换
            return StringToMapConverter.getStatementDownloadResult(responseContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * <pre>
     * 查询订单支付结果
     * status 0表示成功，非0表示失败此字段是通信标识，非交易标识
     * 交易是否成功需要查看 trade_state 来判断
     * result_code 0表示成功，非0表示失败
     * @param queryOrderRequest 查询参数
     * @return 订单结果
     */

    public QueryOrderResult queryOrder(QueryOrderRequest queryOrderRequest)  {
        try {
            queryOrderRequest.checkAndSign();
            //进行http调用
            String responseContent = HttpUtils.post(swiftPassPayConfig.getPayBaseUrl(), queryOrderRequest.toXML());
            //将请求结果进行转换
            QueryOrderResult queryOrderResult = BasePayResult.fromXML(responseContent, QueryOrderResult.class);
            //校验请求结果
            queryOrderResult.checkResult(true);
            return queryOrderResult;
        } catch (PayException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 订单退款
     *
     * @param refundOrderRequest 退款请求参数
     * @return 退款结果信息
     */
    public RefundOrderResult refundOrder(RefundOrderRequest refundOrderRequest)  {
        try {
            //校验并进行加密，此处会塞入service类型
            refundOrderRequest.checkAndSign();
            //进行http调用
            String responseContent = HttpUtils.post(swiftPassPayConfig.getPayBaseUrl(), refundOrderRequest.toXML());
            RefundOrderResult refundOrderResult = BasePayResult.fromXML(responseContent, RefundOrderResult.class);
            //校验请求结果
            refundOrderResult.checkResult(true);
            return refundOrderResult;
        } catch (PayException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 订单退款查询
     * @param refundQueryOrderRequest 退款查询请求参数
     * @return 退款查询结果
     */
    public RefundQueryOrderResult refundQuery(RefundQueryOrderRequest refundQueryOrderRequest) {
        try {
            //校验并进行加密，此处会塞入service类型
            refundQueryOrderRequest.checkAndSign();
            //进行http调用
            String responseContent = HttpUtils.post(swiftPassPayConfig.getPayBaseUrl(), refundQueryOrderRequest.toXML());
            RefundQueryOrderResult refundOrderResult = BasePayResult.fromXML(responseContent, RefundQueryOrderResult.class);
            //校验请求结果
            refundOrderResult.checkResult(true);
            return refundOrderResult;
        } catch (PayException e) {
            throw new RuntimeException(e);
        }
    }

}
