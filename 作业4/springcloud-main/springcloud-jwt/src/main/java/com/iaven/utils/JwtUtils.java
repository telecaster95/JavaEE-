package com.iaven.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String createJwt(String username){
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 生成 token：头部+载荷+签名
        return JWT.create()
                .withHeader(header)
                .withClaim("username",username)
                .withExpiresAt(new Date(System.currentTimeMillis()+jwtExpirationMs))
                .sign(algorithm);
    }

    /**
     * 验证成功会返回一个username-claim的键值对
     * @param token
     * @return
     */
    public Map<String, Claim> parseJwt(String token) {


        Map<String, Claim> claims = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            claims = jwt.getClaims();
            return claims;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
