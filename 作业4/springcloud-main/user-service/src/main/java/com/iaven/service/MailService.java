package com.iaven.service;

public interface MailService {
    /**
     * 获取邮箱验证码
     * type：注册，找回
     * @param email
     * @param type
     * @return
     */
    String getCode(String email, int type);


    /**
     *
     * @param email
     * @param type
     * @return
     */
    int sendCodeType(String email,int type);

    /**
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendSimpleMail(String to, String subject, String content);

}
