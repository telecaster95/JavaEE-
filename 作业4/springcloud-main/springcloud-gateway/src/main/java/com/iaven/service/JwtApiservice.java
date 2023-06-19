package com.iaven.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "jwtService")
public interface JwtApiservice {
    @RequestMapping(value = "/access/jwt",method = RequestMethod.POST)
    boolean check(@RequestParam(value = "token")String token);
}
