package com.yufop.tran.Interceptor;



import cn.hutool.json.JSONObject;
import com.yufop.tran.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author laz
 * @date 2022/09/09 14:56
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {


    /**
     * 只有请求带有token并验证成功才能通过拦截器
     * @author Iaven00
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        //获取到token
        String token = request.getHeader("token");
        if (token!=null){
            boolean result= TokenUtil.verify(token);
            if (result){
                System.out.println("通过token验证");
                return true;
            }
        }
        try {
            JSONObject json=new JSONObject();
            json.put("msg","token verify fail");
            json.put("code","500");
            response.getWriter().append(json.toString());
            System.out.println("认证失败，缺少令牌或令牌验证不正确");
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
