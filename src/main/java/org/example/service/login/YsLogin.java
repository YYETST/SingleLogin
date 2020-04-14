package org.example.service.login;

import org.example.base.BaseApi;
import org.example.utils.MD5SignUtil;
import org.example.utils.RequestTool;
import org.example.utils.SHASignUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nishch
 * @version 1.0
 * @date 2020/4/13
 * @des  YonSuite单点登录Service Demo
 */
@Service
public class YsLogin extends BaseApi {

    //YonSuite地址
    @Value("${login.yonsuite}")
    private String yonsuiteUrl;
    //accessToken地址
    @Value("${login.token}")
    private String accessToken;
    //登录web临时token
    @Value("${login.tempToken}")
    private String tempToken;
    //刷新token
    @Value("${login.refreshtoken}")
    private String refreshtoken;

    /**
     * 获取accessToken
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public String getAccessToken(String username, String password) throws Exception {
        String md5password = MD5SignUtil.string2MD5(password);
        String shapassword = SHASignUtil.getSHA1(password);
        Map<String,String> params = new HashMap<String,String>();
        params.put("username",username);
        params.put("md5password",md5password);
        params.put("shapassword",shapassword);
        String url = getUrl(accessToken);
        String result = RequestTool.doPost(url, params);
        //Map<String,Object> result = doPost(url,params,Map.class);
        return result;
    }

    /**
     * 获取登录web临时token
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Map<String,Object> getTempToken(String accessToken) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        params.put("accessToken",accessToken);
        String url = getUrl(tempToken);
        Map<String,Object> result = doPost(url,params,Map.class);
        return result;
    }

    /**
     * 刷新access_token
     * @param refresh_token
     * @return
     * @throws Exception
     */
    public Map<String,Object> getRefreshToken(String refresh_token) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        params.put("refresh_token",refresh_token);
        String url = getUrl(refreshtoken);
        Map<String,Object> result = doPost(url,params,Map.class);
        return result;
    }


}
