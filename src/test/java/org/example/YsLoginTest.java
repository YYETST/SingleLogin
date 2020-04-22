package org.example;

import org.example.service.login.YsLoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class YsLoginTest {

    @Autowired
    YsLoginService ysLogin;

    /**
     * 原有逻辑应该是前台都过按钮请求，然后将链接返回给前端。在前端都过js打开这个url
     * @throws Exception
     */
    @Test
    public void login() throws Exception {
        String username = "18810487612";
        String password = "a132135~!";
        //里面有token，后面使用token即可
        Map<String, Object> access_tokenMap =  ysLogin.getAccessToken(username,password);
        Map<String,Object> data = (Map<String,Object>)access_tokenMap.get("data");
        //授权token
        String access_token = data.get("access_token").toString();
        Map<String,Object> webToken = ysLogin.getTempToken(access_token);
        String tempToken = webToken.get("token").toString();
        String loginUrl = ysLogin.getLoginUrl(tempToken);
        System.out.println("这是可以实现单点登录的地址:"+loginUrl);
    }

}