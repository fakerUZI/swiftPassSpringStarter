package cn.xauat.stric.result.statementDownloadSubclass;


import cn.xauat.stric.annotation.BindName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 曹聪
 * @create 2025/3/1-11:30
 * @description 对账单汇总统计信息
 **/
@Getter
@Setter
@ToString
public class Summary {

    @BindName(columnName = "总交易单数")
    private Integer totalTransactions;

    @BindName(columnName = "总交易额")
    private BigDecimal totalAmount;

    @BindName(columnName = "总退款金额")
    private BigDecimal totalRefund;

    @BindName(columnName = "总企业红包退款金额")
    private BigDecimal totalEnterpriseRedPacketRefund;

    @BindName(columnName = "总手续费金额")
    private BigDecimal totalServiceFee;

    @BindName(columnName = "总实收金额")
    private BigDecimal totalReceivedAmount;
}
