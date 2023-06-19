package com.yufop.tran.util;

public class Global {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int CODEERROR = 105;  // 验证码错误
    public static final int REGISTER = 100; //注册
    public static final int FIND = 101; //找回
    public static final int MODIFY = 102; //修改密码

    public static final String NO_TOKEN = "0";

    public static final String TOKEN_BUCKET_KEY = "token_bucket";//令牌桶key

    public static final Long TOKEN_BUCKET_SIZE = 10L;//令牌桶容量


    public static final String Unshipped = "U";
    public static final String Shipped = "S";
    public static final String FINISH = "F";
    public static final String DELE = "D";

}
