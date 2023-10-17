package com.example.tlias.config;

import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//这个注解代表这个类是个配置类
public class CommonConfig {//这个类演示了怎么将第三方的类交给IOC容器管理成为bean对象，需要用到@Bean注解

    @Bean//声明第三方的bean,将当前方法返回值的对象交给IOC容器管理成为bean对象
    public SAXReader saxReader(){   //这个SAXReader类是解析xml文件的，这里用来演示第三方类
        return new SAXReader();
    }
    /**
     * 通过@Bean注解的name或value属性可以声明bean的名称，如果不指定，默认bean的名称就是方法名。
     * 如果第三方bean需要依赖其它bean对象，直接在bean定义方法中设置形参即可，容器会根据类型自动装配。
     */
}
