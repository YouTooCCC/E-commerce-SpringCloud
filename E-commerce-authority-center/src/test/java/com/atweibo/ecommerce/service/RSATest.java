package com.atweibo.ecommerce.service;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @Description    非对称加密算法，生成公钥和私钥
 * @Author weibo
 * @Data 2022/11/10 20:43
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RSATest {
    @Test
    public void generateKeyBytes() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        /*生成公钥和私钥对*/
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        /*公钥*/
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        /*私钥*/
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        log.info("privateKey:【{}】", Base64.encode(privateKey.getEncoded()));
        log.info("publicKey:【{}】", Base64.encode(publicKey.getEncoded()));

    }
}
