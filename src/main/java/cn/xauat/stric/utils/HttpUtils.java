package cn.xauat.stric.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 曹聪
 * @create 2025/2/24-17:25
 * @description http请求工具类
 **/
@Slf4j
public class HttpUtils {


    // 连接超时时间
    private static final int connectionTimeout = 3000;
    // 套接字超时时间
    private static final int socketTimeout = 3000;

    public static byte[] postForBytes(String url, String requestStr) {
        try {
            // 创建 POST 请求
            HttpRequest request = HttpUtil.createPost(url)
                    .setConnectionTimeout(connectionTimeout)
                    .setReadTimeout(socketTimeout)
                    .header("Content-Type", "application/json")
                    .body(requestStr);

            HttpResponse response = request.execute();

            // 记录日志
            log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据(Base64编码后)】：{}",
                    url, requestStr, Base64.encode(response.bodyBytes()));

            return response.bodyBytes();
        } catch (Exception e) {
            log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}",
                    url, requestStr, e.getMessage());
            // 抛出自定义异常或客户异常
            throw new RuntimeException(e);
        }
    }

    public static String post(String url, String requestStr) {
        try {
            // 创建 POST 请求
            HttpRequest request = HttpUtil.createPost(url)
                    .setConnectionTimeout(connectionTimeout)
                    .setReadTimeout(socketTimeout)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(requestStr);

            HttpResponse response = request.execute();

            String responseString = response.body();

            // 记录日志
            log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}",
                    url, requestStr, responseString);

            return responseString;
        } catch (Exception e) {
            log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());

            throw new RuntimeException(e);
        }
    }
}
