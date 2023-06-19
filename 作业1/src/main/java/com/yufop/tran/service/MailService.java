package com.yufop.tran.service;


import org.springframework.stereotype.Service;



public interface MailService {

    String getCode(String email, int type);
    int sendCodeType(String email, int type);
    void sendSimpleMail(String to, String subject, String content);
}
