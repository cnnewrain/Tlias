package com.example.tlias.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.example.tlias.pojo.Result;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import utils.JwtUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //获取请求的url
        String url = httpServletRequest.getRequestURL().toString();
        log.info("请求的url：{}", url);

        //判断Url是否包含login
        if (url.contains("login")) {
            log.info("登录操作直接放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            //获取请求头的令牌，请求头的名字为token
            String Jwt = httpServletRequest.getHeader("token");
            if (!StringUtils.hasLength(Jwt)) {//判断字符串是否有长度,
                log.info("请求头token为空");
                Result error = Result.error("NOT_LOGIN");
                //返回一个JSON格式的数据，之前是在Controller操作自动将返回值转换为JSON格式返回给前端，现在是过滤器，所以我们要手动转换为JSON格式
                String resultError = JSONObject.toJSONString(error);//将结果转换为JSON格式
                httpServletResponse.getWriter().write(resultError);//用httpServletResponse里面的方法返回给浏览器
                return;
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
                    return;
                }
                log.info("令牌有效");
                filterChain.doFilter(servletRequest, servletResponse);//令牌有效放行
            }
        }

    }
}
/**过滤器Filter和拦截器Interceptor的区别
 * 接口规范不同:过滤器需要实现Filter接口，而拦截器需要实现HandlerInterceptor接口。
 * 拦截范围不同:过滤器Filter会拦截所有的资源，而Interceptor只会拦截Spring环境中的资源
 */