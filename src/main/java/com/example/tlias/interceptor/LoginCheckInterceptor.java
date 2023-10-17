package com.example.tlias.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.tlias.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.JwtUtils;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override//目标资源运行前运行，返回值为true则放行，如果为false则拦截
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        //获取请求的url
        String url = httpServletRequest.getRequestURL().toString();
        log.info("请求的url：{}", url);

        //判断Url是否包含login
        if (url.contains("login")) {
            log.info("登录操作直接放行");
            return true;
        } else {
            //获取请求头的令牌，请求头的名字为token
            String Jwt = httpServletRequest.getHeader("token");
            if (!StringUtils.hasLength(Jwt)) {//判断字符串是否有长度,
                log.info("请求头token为空");
                Result error = Result.error("NOT_LOGIN");
                //返回一个JSON格式的数据，之前是在Controller操作自动将返回值转换为JSON格式返回给前端，现在是过滤器，所以我们要手动转换为JSON格式
                String resultError = JSONObject.toJSONString(error);//将结果转换为JSON格式
                httpServletResponse.getWriter().write(resultError);//用httpServletResponse里面的方法返回给浏览器
                return false;
            } else {
                try {
                    JwtUtils.parseJWT(Jwt);//验证JWT令牌是否正确,如果解析错误，就不正确,所以要抛出异常
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("JWT令牌无效");
                    Result error = Result.error("NOT_LOGIN");
                    //返回一个JSON格式的数据，之前是在Controller操作自动将返回值转换为JSON格式返回给前端，现在是过滤器，所以我们要手动转换为JSON格式
                    String resultError = JSONObject.toJSONString(error);//将结果转换为JSON格式
                    httpServletResponse.getWriter().write(resultError);//用httpServletResponse里面的方法返回给浏览器
                    return false;
                }
                log.info("令牌有效");
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
/**过滤器Filter和拦截器Interceptor的区别
 * 接口规范不同:过滤器需要实现Filter接口，而拦截器需要实现HandlerInterceptor接口。
 * 拦截范围不同:过滤器Filter会拦截所有的资源，而Interceptor只会拦截Spring环境中的资源
 */