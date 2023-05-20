package com.work.springcloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.work.springcloud.beans.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
public class JwtUtils {

    /**创建秘钥*/
    private static final byte[] SECRET = "6MNSobBRCHGIO0fS6MNSobBRCHGIO0fS".getBytes();

    /** 过期时间1个小时*/
    private static final long EXPIRE_TIME = 1000 * 60 * 60;


    /**生成Token*/
    public static String buildJWT(String account) {
        try {
            /**1.创建一个32-byte的密匙*/
            MACSigner macSigner = new MACSigner(SECRET);
            /**2. 建立payload 载体*/
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("bjpowernode")
                    .issuer("http://www.bjpowernode.com")
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .claim("ACCOUNT",account)
                    .build();

            /**3. 建立签名*/
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(macSigner);

            /**4. 生成token*/
            String token = signedJWT.serialize();
            return token;
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**校验token*/
    public static String vaildToken(String token ) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                throw new Exception("Token 无效");
            }

            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                throw new Exception( "Token 已过期");
            }

            //获取载体中的数据
            Object account = jwt.getJWTClaimsSet().getClaim("ACCOUNT");
            //是否有openUid
            if (Objects.isNull(account)){
                throw new Exception( "账号为空");
            }
            return account.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws IOException {

        User user = new User()
                .setId(UUID.randomUUID().toString())
                .setUsername("yueyue")
                .setPassword("1127")
                .setApplicationName("token");

        //user : User{username='yueyue', password='1127'}
        System.out.println("user : "+user);

        String data = new ObjectMapper().writeValueAsString(user);
        //json : {"username":"yueyue","password":"1127"}
        System.out.println("json : "+data);

       /*
       * token :
       * eyJhbGciOiJIUzI1NiJ9
       * .
       * eyJzdWIiOiJianBvd2Vybm9kZSIsIkFDQ09VTlQiOiJ7XCJpZFwiOlwiYmRiM2Q4YmMtNThjYy00YmI3LTllY2QtMjM4Y2I4NDYzOTk3XCIsXCJ1c2VybmFtZVwiOlwieXVleXVlXCIsXCJwYXNzd29yZFwiOlwiMTEyN1wiLFwiYXBwbGljYXRpb25OYW1lXCI6XCJ0b2tlblwifSIsImlzcyI6Imh0dHA6XC9cL3d3dy5ianBvd2Vybm9kZS5jb20iLCJleHAiOjE2ODM4OTE1MjV9
       * .
       * k7DuSzXp2n__hny9fv_5Wu2ECsEFKERmnptF5YE9pns
       *
       * */
        String token = buildJWT(data);
        System.out.println("token : "+token);

        //rawData : {"username":"yueyue","password":"1127"}
        String rawData = vaildToken(token);
        System.out.println("rawData : "+rawData);

    }

}