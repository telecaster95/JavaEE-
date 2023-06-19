package com.yufop.tran.controller;


import com.yufop.tran.annotate.LimitingRequired;
import com.yufop.tran.pojo.table.User;
import com.yufop.tran.service.MailService;
import com.yufop.tran.service.UserService;
import com.yufop.tran.util.ReturnObject;
import com.yufop.tran.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



import static com.yufop.tran.util.Global.*;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;


    @PostMapping("/login")
    @ResponseBody
    @LimitingRequired
    public ReturnObject<User> loginUser(String email,
                                          String password){
        System.out.println(email);
        User user = userService.loginUser(email,password);
        ReturnObject<User> ret = new ReturnObject<>(NO_TOKEN,user);
        if(user==null){
            //登录失败返回0
            return ret;
        }
        else {
            user.setPassword("------");
            String token = TokenUtil.sign(user);
            ret.setCode(token);
            ret.setObject(user);
        }
        return ret;
    }
    @PostMapping("/register")
    @ResponseBody
    @LimitingRequired
    public int register(@RequestBody User user, @RequestParam("code")String codeValue){


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

    @GetMapping("/modifyUserInfo")
    @ResponseBody
    public int modifyUserInfo(Integer userId, String email, String name) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(name);
        return userService.modifyInfo(userId,newUser);

    }


}
