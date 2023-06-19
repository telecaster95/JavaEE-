package com.yufop.tran.Interceptor;

import com.yufop.tran.annotate.LimitingRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import static com.yufop.tran.util.Global.TOKEN_BUCKET_KEY;


/**
 * @author Iaven00
 */
@Component
public class TokenBucketIntercepter implements HandlerInterceptor {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LimitingRequired annotation = method.getAnnotation(LimitingRequired.class);
            //获取注解判断需不需要被限流
            if (annotation != null && annotation.required()) {
                boolean ace = isAllowAccess();
                if (!ace) {
                    System.out.println("系统繁忙，请等待……");
                    return false;
                }
                System.out.println("令牌分配成功！");
            }

            return true;
        }

        // 如果是静态资源则不拦截
        else if(handler instanceof ResourceHttpRequestHandler) {
            System.out.println("静态资源访问");
            return true;
        }
        System.out.println("未知请求");
        return false;
    }

    private boolean isAllowAccess() {
        ListOperations listOperations = redisTemplate.opsForList();
        Long size = listOperations.size(TOKEN_BUCKET_KEY);
        //令牌桶中没有令牌，返回false，这样就起到了限流的作用
        System.out.println("令牌数量：" + size);
        if (size < 1) {
            return false;
        }
        //否则从最右端取出一个令牌
        listOperations.rightPop(TOKEN_BUCKET_KEY);
        return true;
    }



}