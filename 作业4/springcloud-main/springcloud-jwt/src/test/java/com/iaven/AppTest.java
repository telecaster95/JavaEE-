package com.iaven;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.iaven.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */


    @Test
    public void context()
    {

        Claim claim = parseJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODU1NTE0MzEsInVzZXJuYW1lIjoiaWF2ZW4ifQ.igIoxYid8Jlf0CosyiKMs_HvwiNiXiwdR4vZ_ps9Eqw").get("username");
        System.out.println(claim.asString());
    }
    public Map<String, Claim> parseJwt(String token) {


        Map<String, Claim> claims = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("shipSecretKey");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            claims = jwt.getClaims();
            System.out.println("claims is "+claims.toString());
            return claims;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
