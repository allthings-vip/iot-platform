package allthings.iot.dos.monitor.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author :  txw
 * @FileName :  HttpUtil
 * @CreateDate :  2019/6/15
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static Boolean getJSONTest(String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(url);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("执行请求：{}", httpget.getRequestLine());
            }

            new ResponseHandler() {
                @Override
                public Object handleResponse(HttpResponse httpResponse) throws IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    return null;
                }
            };

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000).setConnectionRequestTimeout(3000)
                    .setSocketTimeout(3000).build();
            httpget.setConfig(requestConfig);

            httpclient.execute(httpget, (ResponseHandler) httpResponse -> {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == 404) {
                    throw new IOException();
                }
                return null;
            });
            return true;
        } catch (IOException e) {
            LOGGER.error("HTTP请求异常，url=" + url, e);
            return false;
        }
    }

}
