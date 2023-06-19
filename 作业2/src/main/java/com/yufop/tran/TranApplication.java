package com.yufop.tran;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan("com.yufop.tran.mapper")
public class TranApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranApplication.class, args);
    }

}
