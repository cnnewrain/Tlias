package com.example.tlias.config;

import com.example.tlias.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//这个注解代表当前的类是配置类
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {//addPathPatterns需要拦截的路径,excludePathPatterns排除拦截的路径
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
/**过滤器Filter和拦截器Interceptor的区别
 * 接口规范不同:过滤器需要实现Filter接口，而拦截器需要实现HandlerInterceptor接口。
 * 拦截范围不同:过滤器Filter会拦截所有的资源，而Interceptor只会拦截Spring环境中的资源
 */