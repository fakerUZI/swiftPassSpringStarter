package com.github.fakerUZI.result;

import com.github.fakerUZI.result.statementDownloadSubclass.Summary;
import com.github.fakerUZI.result.statementDownloadSubclass.TradeRecord;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author 曹聪
 * @create 2025/3/1-11:31
 * @description 对账单下载结果
 **/
@Getter
@ToString
public class StatementDownloadResult {

    private final List<TradeRecord> records;
    private final Summary summary;

    public StatementDownloadResult(List<TradeRecord> records, Summary summary) {
        this.records = records;
        this.summary = summary;
    }

}
