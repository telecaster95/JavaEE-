package com.iaven.service;


import com.iaven.utils.ReturnObject;
import com.iaven.utils.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "userservice")
public interface userService {

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    ReturnObject<User> login(@RequestParam(value = "email")String email, @RequestParam(value = "password")String password);

}
