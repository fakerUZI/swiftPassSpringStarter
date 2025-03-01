package cn.xauat.stric.utils;

/**
 * @author 曹聪
 * @create 2025/3/1-13:46
 * @description 将对账单的结果转换为定义的对象类型
 **/

import cn.xauat.stric.annotation.BindName;
import cn.xauat.stric.result.StatementDownloadResult;
import cn.xauat.stric.result.statementDownloadSubclass.Summary;
import cn.xauat.stric.result.statementDownloadSubclass.TradeRecord;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StringToMapConverter {
    public static void main(String[] args) throws IllegalAccessException {
        String input = "?交易时间,公众账号ID,第三方商户号,商户号,子商户号,设备编号,平台订单号,第三方订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,平台退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率,终端类型,对账标识,门店编号,商户名称,收银员,子商户ID,免充值券金额,实收金额,结算金额,扩展字段2,扩展字段3,扩展字段4\n" +
                "`2025-02-27 09:45:17,`wx802a07906af1be15,`742677995,`204550051431,`204540051457,`,`20250227052620712721052633846485,`4200002622202502279970812889,`1894926725968695296,`oMKaouIrd-OANAmLU_GaJ1z83mvQ,`pay.weixin.jspayv3.f6,`支付成功,`OTHERS,`CNY,`2.00,`0.00,`,`,`,`,`,`,`甘J8D513,`,`0.01,`0.300%,`API,`0,`,`新能源-惠民佳苑车场,`,`oAEHG6z9RZsba0BfoK9PovT5bKk8,`0.00,`2.00,`1.99,`,`,`\n" +
                "`2025-02-27 10:35:01,`wx802a07906af1be15,`742677995,`204550051431,`204540051457,`,`20250227052623693713052633867153,`4200002531202502279231048396,`1894939221072941056,`oMKaouIrd-OANAmLU_GaJ1z83mvQ,`pay.weixin.jspayv3.f6,`支付成功,`OTHERS,`CNY,`2.00,`0.00,`,`,`,`,`,`,`甘JW5631,`,`0.01,`0.300%,`API,`0,`,`新能源-惠民佳苑车场,`,`oAEHG6z9RZsba0BfoK9PovT5bKk8,`0.00,`2.00,`1.99,`,`,`\n" +
                "`2025-02-27 10:39:00,`wx802a07906af1be15,`742677995,`204550051431,`204540051457,`,`20250227052623933096052613672043,`4200002524202502276349033866,`1894940227575877632,`oMKaouIrd-OANAmLU_GaJ1z83mvQ,`pay.weixin.jspayv3.f6,`支付成功,`OTHERS,`CNY,`2.00,`0.00,`,`,`,`,`,`,`甘JW5631,`,`0.01,`0.300%,`API,`0,`,`新能源-惠民佳苑车场,`,`oAEHG6z9RZsba0BfoK9PovT5bKk8,`0.00,`2.00,`1.99,`,`,`\n" +
                "`2025-02-27 11:01:56,`wx802a07906af1be15,`742677995,`204550051431,`204540051457,`,`20250227052625300218052613681746,`4200002555202502273092324686,`1894945966625263616,`oMKaouIrd-OANAmLU_GaJ1z83mvQ,`pay.weixin.jspayv3.f6,`支付成功,`OTHERS,`CNY,`4.00,`0.00,`,`,`,`,`,`,`甘J8D513,`,`0.01,`0.300%,`API,`0,`,`新能源-惠民佳苑车场,`,`oAEHG6z9RZsba0BfoK9PovT5bKk8,`0.00,`4.00,`3.99,`,`,`\n" +
                "`2025-02-27 11:09:39,`wx802a07906af1be15,`742677995,`204550051431,`204540051457,`,`20250227052625773016052633882307,`4200002526202502276430953988,`1894947950619136000,`oMKaouIrd-OANAmLU_GaJ1z83mvQ,`pay.weixin.jspayv3.f6,`支付成功,`OTHERS,`CNY,`4.00,`0.00,`,`,`,`,`,`,`甘J8D513,`,`0.01,`0.300%,`API,`0,`,`新能源-惠民佳苑车场,`,`oAEHG6z9RZsba0BfoK9PovT5bKk8,`0.00,`4.00,`3.99,`,`,`\n" +
                "总交易单数,总交易额,总退款金额,总企业红包退款金额,总手续费金额,总实收金额\n" +
                "`5,`14.00,`0.00,`0,`0.05,`14.00";

        StatementDownloadResult statementDownloadResult = getStatementDownloadResult(input);
        System.out.println(statementDownloadResult);
    }


    @SuppressWarnings("All")
    public static StatementDownloadResult getStatementDownloadResult(String input) throws IllegalAccessException {
        Map<String, Object> stringObjectMap = parseTransactionData(input);
        //构造返回明细数据
        List<TradeRecord> tradeRecords = new ArrayList<>();
        //开始写入数据
        List<Map<String, String>> details = (List<Map<String, String>>) stringObjectMap.get("details");
        if (!details.isEmpty()) {
            for (Map<String, String> detail : details) {
                TradeRecord tradeRecord = new TradeRecord();
                //获取所有属性值
                Field[] declaredFields = tradeRecord.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    //获取绑定的字段名
                    BindName annotation = field.getAnnotation(BindName.class);
                    String columnName = annotation.columnName();
                    if (StringUtils.isNotBlank(columnName)) {
                        String string = detail.get(columnName).replaceAll("`", "");
                        if (StringUtils.isNotBlank(string)) {
                            field.setAccessible(true);
                            Class<?> type = field.getType();
                            if (type == String.class) {
                                field.set(tradeRecord, string);
                            } else if (type == BigDecimal.class) {
                                field.set(tradeRecord, new BigDecimal(string));
                            } else if (type == LocalDateTime.class) {
                                field.set(tradeRecord, LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            }
                        }
                    }
                }
                tradeRecords.add(tradeRecord);
            }
        }
        //构造返回总计数据
        Summary summary = new Summary();
        Map<String, String> summaryMap = (Map<String, String>) stringObjectMap.get("summary");
        Field[] declaredFields = summary.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            BindName annotation = declaredField.getAnnotation(BindName.class);
            String columnName = annotation.columnName();
            if (StringUtils.isNotBlank(columnName)) {
                String string = summaryMap.get(columnName).replaceAll("`", "");
                if (StringUtils.isNotBlank(string)) {
                    declaredField.setAccessible(true);
                    Class<?> type = declaredField.getType();
                    if (type == String.class) {
                        declaredField.set(summary, string);
                    } else if (type == BigDecimal.class) {
                        declaredField.set(summary, new BigDecimal(string));
                    } else if (type == LocalDateTime.class) {
                        declaredField.set(summary, LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                }
            }
        }
        return new StatementDownloadResult(tradeRecords, summary);
    }

    private static Map<String, Object> parseTransactionData(String data) {
        List<String> lines = Arrays.stream(data.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();

        if (lines.isEmpty()) {
            return result;
        }

        // 处理表头
        String headerLine = lines.get(0).startsWith("?") ? lines.get(0).substring(1) : lines.get(0);
        String[] headers = headerLine.split(",");

        // 处理交易明细
        List<Map<String, String>> details = new ArrayList<>();
        int currentLine = 1;

        // 遍历直到遇到汇总行或结束
        while (currentLine < lines.size() && !lines.get(currentLine).startsWith("总交易单数")) {
            // 保留空字段
            String[] values = lines.get(currentLine).split(",", -1);
            Map<String, String> detail = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = i < values.length ? values[i] : "";
                detail.put(headers[i].trim(), value.trim());
            }
            details.add(detail);
            currentLine++;
        }

        result.put("details", details);

        // 处理汇总信息
        if (currentLine < lines.size()) {
            String[] summaryHeaders = lines.get(currentLine).split(",");
            currentLine++;
            if (currentLine < lines.size()) {
                String[] summaryValues = lines.get(currentLine).split(",", -1);
                Map<String, String> summary = new LinkedHashMap<>();
                for (int i = 0; i < summaryHeaders.length; i++) {
                    String value = i < summaryValues.length ? summaryValues[i] : "";
                    summary.put(summaryHeaders[i].trim(), value.trim());
                }
                result.put("summary", summary);
            }
        }

        return result;
    }

}
