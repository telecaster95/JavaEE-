package com.iaven;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.iaven.mapper")
public class userApplication {
    public static void main(String[] args){SpringApplication.run(userApplication.class, args);}
}
