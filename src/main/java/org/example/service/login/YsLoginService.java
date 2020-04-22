package org.example.service.login;


import com.yonyou.yht.sdk.UserCenter;
import org.example.base.BaseApi;
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
public class YsLoginService extends BaseApi {


    /**
     * 获取accessToken
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public Map<String,Object> getAccessToken(String username, String password) throws Exception {
        String response =UserCenter.createAccessToken(sysId, username, password, clientId);
        Map<String,Object> result = mapper.readValue(response,Map.class);
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
        String response = UserCenter.getLoginTokenByAccessToken(accessToken);
        Map<String,Object> result = mapper.readValue(response,Map.class);
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
        String response = UserCenter.refreshAccessToken(refresh_token);
        Map<String,Object> result = mapper.readValue(response,Map.class);
        return result;
    }


}
