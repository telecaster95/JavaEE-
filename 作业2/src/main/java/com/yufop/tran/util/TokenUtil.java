package com.yufop.tran.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yufop.tran.pojo.table.User;

import java.util.Date;

/**
 * @author laz
 * @date 2022/09/09 14:55
 */
public class TokenUtil {


    /**
     *token到期时间60s
     */
    private static final long EXPIRE_TIME= 15*1000;
    /**
    *密钥
    */
    private static final String TOKEN_SECRET="monitor_practice_124532";

    /**
     * 创建一个token
     * @param user
     * @return
     */
    public static String sign(User user){
        String token=null;
        try {
            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("auth0")
                    //存放数据
                    .withClaim("username",user.getEmail())
                    .withClaim("password",user.getPassword())
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException|JWTCreationException je) {

        }
        return token;
    }
    /**
     * 对token进行验证
     * @param token
     * @return
     */
    public static Boolean verify(String token){
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            System.out.println("username: " + TokenUtil.getUserName(token));
            System.out.println("过期时间：    " + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException |JWTVerificationException e) {
            System.out.println("error message:"+e.getMessage());
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }

    /**
     * 获取用户名
     */
    public static String getUserName(String token){
        try{
            DecodedJWT jwt=JWT.decode(token);
            return  jwt.getClaim("username").asString();
        }catch (JWTDecodeException e)
        {
            return null;
        }
    }
}