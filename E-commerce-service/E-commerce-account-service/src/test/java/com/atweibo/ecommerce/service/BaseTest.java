package com.atweibo.ecommerce.service;

import com.atweibo.ecommerce.filter.AccessContext;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description 测试用例基类，填充登录用户信息
 * @Author weibo
 * @Data 2022/11/19 20:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class BaseTest {
    protected final LoginUserInfo loginUserInfo = new LoginUserInfo(10L,"bobo@qq.com");

    @Before
    public void init(){
        AccessContext.setLoginUserInfo(loginUserInfo);
    }

    @After
    public void destroy(){
        AccessContext.clearLoginUserInfo();
    }

}
