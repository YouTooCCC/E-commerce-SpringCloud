package com.atweibo.ecommerce.service.impl;



import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.constant.AuthorityConstant;
import com.atweibo.ecommerce.constant.CommonConstant;
import com.atweibo.ecommerce.dao.EcommerceUserDao;

import com.atweibo.ecommerce.entity.EcommerceUser;
import com.atweibo.ecommerce.service.IJWTService;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * @Description 实现JWTtoken接口
 * @Author weibo
 * @Data 2022/11/11 9:59
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JWTServiceImpl implements IJWTService {

    private final EcommerceUserDao ecommerceUserDao;

    public JWTServiceImpl(EcommerceUserDao ecommerceUserDao) {
        this.ecommerceUserDao = ecommerceUserDao;
    }


    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username, password, 0);
        /*使用0，是因为小于等于0时，设置的是默认的超时时间*/
    }


    @Override
    public String generateToken(String username, String password, int expire) throws Exception {
        /*首先验证用户是否能通过授权校验，即输入的用户名和密码能否匹配数据表记录*/
        EcommerceUser ecommerUser = ecommerceUserDao.findByUsernameAndPassword(username, password);
        if (ecommerUser == null) {
            log.info("没有找到用户名为：【{}】，密码：【{}】", username, password);
            return null;
        }

        /*JWT Token中放入对象，即JWT中存储的信息，后端拿到这些信息就可以直到是哪个用户在操作。*/
        LoginUserInfo loginUserInfo = new LoginUserInfo(ecommerUser.getId(), ecommerUser.getUsername());

        if (expire <= 0) {
            /*使用默认超时时间*/
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }
        /*计算超时时间*/
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                /*声明payload，就是真实的数据，是key-value*/
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                /*jwt 的id，标识jwt*/
                .setId(UUID.randomUUID().toString())
                /*jwt的过期时间*/
                .setExpiration(expireDate)
                /*jwt签名   加密，使用RS算法加密*/
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                /*生成JWT信息*/
                .compact();
    }


    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {
        EcommerceUser oldUser = ecommerceUserDao.findByUsername(usernameAndPassword.getUsername());
        if (oldUser != null) {
            log.error("用户名为：[{}]已经存在", oldUser.getUsername());
            return null;
        }

        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(usernameAndPassword.getUsername());
        ecommerceUser.setPassword(usernameAndPassword.getPassword());
        ecommerceUser.setExtraInfo("{}");

        /*注册一个新用户，写一条记录到记录表中*/
        /*int update = ecommerceUserDao.save(ecommerUser);*/
        ecommerceUser = ecommerceUserDao.save(ecommerceUser);

        log.info("用户注册成功：[{}]，[{}]", ecommerceUser.getUsername(), ecommerceUser.getId());

        return generateToken(ecommerceUser.getUsername(), ecommerceUser.getPassword());
    }

    /*根据本地存储的私钥获取到PrivateKey对象，反序列化等等操作*/
    private PrivateKey getPrivateKey() throws Exception {

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATEKEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }
}
