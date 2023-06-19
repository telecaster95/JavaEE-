package com.iaven.controller;


import com.iaven.pojo.table.User;
import com.iaven.service.MailService;
import com.iaven.service.UserService;
import com.iaven.utils.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

import static com.iaven.utils.Global.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;

    @PostMapping("/login")
    @ResponseBody
    public ReturnObject<User> loginUser(String email,
                                        String password){

        ReturnObject<User> ret = new ReturnObject<>("F",null);
        if(email==null || password==null) {return ret;}
        User user = userService.loginUser(email,password);
        if(user==null){
            //登录失败返回0
            return ret;
        }
        else {
            user.setPassword("------");
            ret.setCode("S");
            ret.setObject(user);
        }
        return ret;
    }


    @PostMapping("/register")
    @ResponseBody
    public int register(@RequestBody User user, String codeValue){


        String code = mailService.getCode(user.getEmail(),REGISTER);
        if(codeValue.compareTo(code)!=0){
            return CODEERROR;
        }
        if(userService.registerUser(user)!=null){
            mailService.sendSimpleMail(user.getEmail() ,"Register Notice",
                    "Register Notice    "+"Register successfully! Welcome to use our products, if you have any " +
                            "comments, please feel free to feedback, we will actively improve, thank you.");
            return SUCCESS;
        }
        return FAIL;
    }

    @GetMapping("/sendcode")
    @ResponseBody
    public int send_code(String email,int type){
        if(email==null){
            return FAIL;
        }
        return mailService.sendCodeType(email,type);
    }

    @GetMapping("/getcode")
    @ResponseBody
    public String get_code(String email,int type){
        if(email==null){
            return null;
        }
        return mailService.getCode(email,type);
    }




}
