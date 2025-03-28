package com.github.fakerUZI.request;

import com.github.fakerUZI.base.BasePayRequest;
import com.github.fakerUZI.enums.StatementEnum;
import com.github.fakerUZI.exception.PayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author 曹聪
 * @create 2025/3/1-10:00
 * @description 对账单下载请求类
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@XStreamAlias("xml")
public class StatementDownloadRequest extends BasePayRequest {


    /**
     * 账单日期
     */
    @XStreamAlias("bill_date")
    private String billDate;

    /**
     * 账单类型
     */
    @XStreamAlias("bill_type")
    private String billType;

    public void setBillDate(LocalDate localDate) {
        this.billDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public void setBillType(StatementEnum statementEnum) {
        this.billType = statementEnum.getType();
    }

    @Override
    protected void checkConstraints() throws PayException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {

    }

    @Override
    public void checkAndSign() throws PayException {
        //对账单下载服务为可选类型
        //如果未填写则给默认值
        //下载单个商户时的对账单
        if (StringUtils.isBlank(this.service)) {
            this.service = "pay.bill.merchant";
        }
        super.checkAndSign();
    }
}
