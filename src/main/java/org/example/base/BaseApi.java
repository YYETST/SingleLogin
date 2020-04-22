package org.example.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.utils.RequestTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author nishch
 * @version 3.0
 * @date 2020/3/11
 * @des  自建应用token替换为ISV生态应用token
 */
@Component
public class BaseApi {


    @Value("${sysid}")
    protected String sysId;
    @Value("${client_id}")
    protected String clientId;
    //正式环境友互通地址
    @Value("${login.yht}")
    protected String loginYht;
    //YS访问地址
    @Value("${login.yonsuite}")
    protected String loginYS;


    private static final Logger logger = LoggerFactory.getLogger(BaseApi.class);
    //设置默认的查询页码
    public static final String pageIndex ="1";
    //设置默认的每页显示数量
    public static final String pageSize = "100";

    protected static ObjectMapper mapper = new ObjectMapper();

    private static Gson gson = new Gson();

    public  <T> T doGet(String requestUrl, Map<String, Object> paramMap, Class<T> type) throws Exception {
        return mapper.readValue(getRequestData(RequestTool.doGet(requestUrl, paramMap,false)), type);
    }

    public  <T> T doGet(String requestUrl, Map<String, Object> paramMap, TypeReference<T> typeReference) throws Exception {
        return mapper.readValue(getRequestData(RequestTool.doGet(requestUrl, paramMap,false)), typeReference);
    }

    public  <T> T doGetMark(String requestUrl, Map<String, Object> paramMap, Class<T> type) throws Exception {
        return mapper.readValue(getRequestData(RequestTool.doGet(requestUrl, paramMap,true)), type);
    }

    public  <T> T doGetMark(String requestUrl, Map<String, Object> paramMap, TypeReference<T> typeReference) throws Exception {
        return mapper.readValue(getRequestData(RequestTool.doGet(requestUrl, paramMap,true)), typeReference);
    }

    public  <T> T doPost(String requestUrl, Object param, Class<T> type) throws Exception {
        return mapper.readValue(getRequestData(RequestTool.doPost(requestUrl, param)), type);
    }

    public  <T> T doPost(String requestUrl, Object param, TypeReference<T> typeReference) throws Exception {
        return mapper.readValue(getRequestData(RequestTool.doPost(requestUrl, param)), typeReference);
    }

    protected  String getRequestData(String json) throws Exception {
        String data = null;
        Map<String,Object> result = gson.fromJson(json,Map.class);
        if(ResultCode.SUCCESS.getIndex().equals(result.get(ResultCode.SUCCESS.getName()))
                ||ResultCode.SUCCESS2.getIndex().equals(result.get(ResultCode.SUCCESS2.getName()))){
            data = gson.toJson(result.get("data"));
        }else{
            logger.error(result.get("message").toString());
            throw  new Exception(result.get("message").toString());
        }
        return data;
    }


    /**
     * 获取拼接的YS单点登录地址
     */
    public String getLoginUrl(String tempToken) throws UnsupportedEncodingException {
        return loginYht+"?sysid="+sysId+"&service="+ URLEncoder.encode(loginYS,"UTF-8") +"&token="+tempToken;
    }


}
