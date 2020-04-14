package org.example;

import org.example.service.login.YsLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class YsLoginTest {

    @Autowired
    YsLogin ysLogin;

    @Test
    public void login() throws Exception {
        String username = "*****";
        String password = "*****";
        String access_tokenMap =  ysLogin.getAccessToken(username,password);
    }

}