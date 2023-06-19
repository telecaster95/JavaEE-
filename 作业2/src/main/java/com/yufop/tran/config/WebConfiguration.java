package com.yufop.tran.config;


import com.yufop.tran.Interceptor.TokenBucketIntercepter;
import com.yufop.tran.Interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Iaven00
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private TokenBucketIntercepter tokenBucketIntercepter;
    /**
     * 配置拦截器、拦截路径
     * 每次请求到拦截的路径，就会去执行拦截器中的方法
     * @param
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //排除拦截，除了登录，其他都拦截
        registry.addInterceptor(tokenInterceptor)  //token登录验证拦截
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/mail/code"
                ).addPathPatterns("/user/**");

        registry.addInterceptor(tokenBucketIntercepter) // token令牌流量拦截
                .excludePathPatterns(
                        "/**/*.js",
                        "/**/*.vue"
                )
                .addPathPatterns("/user/**");

        WebMvcConfigurer.super.addInterceptors(registry);

    }

}