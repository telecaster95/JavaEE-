package com.yufop.tran.controller;


import com.yufop.tran.annotate.LimitingRequired;
import com.yufop.tran.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.yufop.tran.util.Global.*;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;


    @GetMapping("/code")
    @ResponseBody
    @LimitingRequired
    public int get_rigister_code(String email){
        return mailService.sendCodeType(email,REGISTER);
    }

}
