package com.iaven.controller;


import com.auth0.jwt.interfaces.Claim;
import com.iaven.service.userService;
import com.iaven.utils.JwtUtils;
import com.iaven.utils.ReturnObject;
import com.iaven.utils.User;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/access")
public class Authcontroller {



    @Autowired
    userService service;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 登录，返回用户信息和token
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    @CircuitBreaker(name = "backendA",fallbackMethod = "loginHandler")
    public ReturnObject<User> login(String email, String password) {
        System.out.println("email:"+email+"     pass:"+password);
        ReturnObject<User>  user = service.login(email,password);
        // 登录成功写入token
        if(user.getCode().compareTo("S") == 0){
            user.setCode(jwtUtils.createJwt(user.getObject().getUsername()));
        }
        return user;
    }


    public ReturnObject<User> loginHandler(String email, String password,Throwable t){
        System.out.println("请求的服务终止,来源:user-service");
        return new ReturnObject<>("请求的服务终止,来源:user-service",null);
    }

    public ReturnObject<User> loginThreadHandler(String email, String password,Throwable t){
        System.out.println("线程池耗尽！:");
        return new ReturnObject<>("线程池耗尽！",null);
    }


    /**
     * token验证
     * @param token
     * @return boolean
     */
    @PostMapping("/jwt")
    public boolean token_check(String token){
        System.out.println("token is "+token);

        Map<String, Claim> claimmap  = jwtUtils.parseJwt(token);

        if(claimmap == null) {
            System.out.println("calimmap is null !");
            return false;
        }
        Claim claim = claimmap.get("username");
        if(claim==null) {
            return false;
        }
        return  true;
    }


    @GetMapping("/test")
    public String test(){
        return "this is test";
    }
}
