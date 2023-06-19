package com.yufop.tran.service.impl;


import com.yufop.tran.service.MailService;
import com.yufop.tran.util.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MailServiceImpl
 * @Description: //TODO
 * @Author wyq
 * @Date 2022/4/18 21:50
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private RedisTemplate redisTemplate;



    @Value("${spring.mail.username}")
    private String UserName;//获得配置文件中的username

    @Autowired
    private JavaMailSender mailSender;//注入发送邮件的bean


    @Override
    public String getCode(String email, int type) {
        String key = email + "_";
        if (type == Global.REGISTER){
            key += "register_code";
        }else if(type == Global.FIND){
            key += "find_code";
        }
        else if(type == Global.MODIFY){
            key += "modify_code";
        }
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null){
            return "";
        }
        return (String) result;
    }




    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom("1245325661@qq.com");
        //收件人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }


    @Override
    public int sendCodeType(String email, int type) {
        long codeL = System.nanoTime();
        //先生成6位验证码
        String codeStr = Long.toString(codeL);
        codeStr = codeStr.substring(codeStr.length() - 8, codeStr.length() - 2);
        String key_code = email;
        String title = "";
        if(type == Global.REGISTER){
            key_code += "_register_code";
            title  = "Registration verification code";
        }
        else if(type == Global.FIND){
            key_code += "_find_code";
            title  = "Retrieve password verification code";
        }
        else if(type == Global.MODIFY){
            key_code += "_modify_code";
            title  = "Change password verification code";
        }
        redisTemplate.opsForValue().set(key_code,codeStr,60*5,TimeUnit.SECONDS);//验证码有效时间是5分钟
        String content = "Welcome to the warehouse management system, your verification code is:"+ codeStr +",Limited time of five minutes";
        sendSimpleMail(email,title,content);
        return Global.SUCCESS;
    }

}
