package allthings.iot.des.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.common.msg.DeviceEventMsg;

import java.io.IOException;

/**
 * @author tyf
 * @date 2019/01/15 17:46
 */
public class HttpSyncService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSyncService.class);
    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static CloseableHttpClient httpClient;

    private static int CONNECT_TIMEOUT = 1000;
    private static int SOCKET_TTIMEOUT = 1000;
    private static int CONNECTION_REQUEST_TIMEOUT = 1000;

    static {
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(64);
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    public static void requestPost(String routerUrl, String params, DeviceEventMsg msg) throws Exception {
        final HttpPost httpPost = new HttpPost(routerUrl);
        CloseableHttpResponse rs = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                    .setSocketTimeout(SOCKET_TTIMEOUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("User-Agent", "deneb-client");
            httpPost.setEntity(dealParams(params));
            LOGGER.info("推送事件开始：推送地址：{}， 推送内容：{}", routerUrl, params);
            rs = httpClient.execute(httpPost);
            if (rs.getStatusLine().getStatusCode() != 200) {
                httpPost.abort();
                LOGGER.warn("设备事件推送失败，推送地址:{}, 推送内容:{}", routerUrl, params);
                LOGGER.warn("事件推送失败消息：{}", JSON.toJSONString(msg));
                throw new RouterException("事件推送失败");
            }
            LOGGER.info("推送事件成功，推送地址：{}， 推送内容：{}", routerUrl, params);
        } finally {
            httpPost.abort();
            if (rs != null) {
                try {
                    rs.close();
                } catch (IOException ie) {
                    LOGGER.error("请求关闭异常，异常信息：{}", ie);
                }
            }
        }
    }

    private static StringEntity dealParams(String params) {
        StringEntity entity = new StringEntity(params, "UTF-8");
        entity.setContentType("application/json;charset=utf-8");
        entity.setContentEncoding("UTF-8");
        return entity;
    }
}
