package com.yufop.tran.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.yufop.tran.util.Global.TOKEN_BUCKET_KEY;
import static com.yufop.tran.util.Global.TOKEN_BUCKET_SIZE;


@Component
@ConfigurationProperties(
        prefix = "server",
        ignoreUnknownFields = true
)
public class ApplicationRunnerImpl implements ApplicationRunner {


    private Integer port;

    @Autowired
    private RedisTemplate redisTemplate;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
        if (port.compareTo(8088) == 0) {
            addTokenBucket();
        }
    }
    private void addTokenBucket() {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ListOperations listOperations = redisTemplate.opsForList();
                //获取队列的长度，当redis队列不存在返回0
                Long size = listOperations.size(TOKEN_BUCKET_KEY);

                if (size.compareTo(TOKEN_BUCKET_SIZE) == -1) {  // 如果size比桶容量小
                    long currentTimeMillis = System.currentTimeMillis();
                    System.out.println("添加令牌---线程：" + Thread.currentThread().getName() + ",执行时间:" + currentTimeMillis + "-" + port);
                    listOperations.leftPush(TOKEN_BUCKET_KEY, String.valueOf((new Date(currentTimeMillis))));
                }
            }
        };
        //每100ms添加一个令牌，即限制了每秒钟请求次数最多有10个请求+预存的10的令牌（如果有突发流量，每秒钟最多请求次数接近10）
        scheduled.scheduleAtFixedRate(runnable, 5, 100, TimeUnit.MILLISECONDS);
    }

}
