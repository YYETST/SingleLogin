package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class RequestTool {

    private static final String HEADER_CONTENT_JSON = "application/json";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static PoolingHttpClientConnectionManager cm = null;

    private static CloseableHttpClient httpClient;

    /**
     * 记录开放平台请求结果
     */
    public static class Response {
        /**
         * 该请求的 http 状态码
         * 200 为正常的返回结果
         */
        private int status;

        /**
         * 请求返回消息
         * 当 status == 200 时会返回 response body 中的字符串
         * 当 status !== 200 时会返回具体的错误信息
         */
        private String result;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    static{
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(500);
        cm.setDefaultMaxPerRoute(50);

        RequestConfig globalConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)         // 连接池获取连接超时
                .setConnectTimeout(5000)                   // 连接建立超时
                .setSocketTimeout(20000)                    // 等待响应超时
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .build();

        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig).build();
    }

    private static CloseableHttpClient getHttpClient(){
        return httpClient;
    }

    /**
     * @param requestUrl
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static String doGet(String requestUrl, Map<String, Object> paramMap,boolean mark) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        StringBuilder param = mark?new StringBuilder("?"):new StringBuilder("&");
        if (paramMap != null) {
            for(Map.Entry<String, Object> entry: paramMap.entrySet()) {
                param.append(entry.getKey());
                param.append("=");
                param.append(entry.getValue());
                param.append("&");
            }
            param.deleteCharAt(param.length() - 1);
        }
        System.out.println("这是请求参数:"+param);
        String url = requestUrl + param;
        HttpGet get = new HttpGet(url);
        String responseString = httpClient.execute(get, response -> EntityUtils.toString(response.getEntity()));
        System.out.println("这是返回数据："+responseString);
        get.releaseConnection();
        return responseString;
    }

    /**
     * @param requestUrl
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static String doGet(String requestUrl, Map<String, Object> paramMap) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        StringBuilder param = new StringBuilder("?");
        if (paramMap != null) {
            for(Map.Entry<String, Object> entry: paramMap.entrySet()) {
                param.append(entry.getKey());
                param.append("=");
                param.append(entry.getValue());
                param.append("&");
            }
            param.deleteCharAt(param.length() - 1);
        }
        System.out.println("这是请求参数:"+param);
        String url = requestUrl + param;
        HttpGet get = new HttpGet(url);
        String responseString = httpClient.execute(get, response -> EntityUtils.toString(response.getEntity()));
        System.out.println("这是返回数据："+responseString);
        get.releaseConnection();
        return responseString;
    }

    public static String doPost(String requestUrl, Object param) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post=new HttpPost(requestUrl);
        ObjectMapper mapper = new ObjectMapper();
        String requestParam = mapper.writeValueAsString(param);
        System.out.println("这是请求参数:"+requestParam);
        post.setEntity(new StringEntity(requestParam));
        String responseString = httpClient.execute(post, response -> EntityUtils.toString(response.getEntity()));
        System.out.println("这是返回数据："+responseString);
        return responseString;
    }


}
