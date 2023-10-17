package com.example.tlias.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.tlias.mapper.OperateLogMapper;
import com.example.tlias.pojo.OperateLog;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.JwtUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
@Aspect
public class LogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**这个类是用来测试注解@annotataion切入点表达式的
     *这个aop类的代码意思是在用户查询Emp表数据的时候记录日志，包括用户的id，操作方法等等。所以先获取用户id，方法名字，参数等等然后插入创建的日志表中
     * 这个aop作用范围是empController中的加了MyAopLog注解的方法
     * @param joinPoint
     * annotation --切入点表达式的一种写法，基于注解,要先自定义一个注解,然后在需要被通知的方法上面加上注解即可
     * 切面类里面的写法为全类名
     */
    @Around("@annotation(com.example.tlias.annotation.MyAopLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint)throws Throwable{

        //操作人ID
        String jwt= httpServletRequest.getHeader("token");
        Claims claims=JwtUtils.parseJWT(jwt);
        Integer operateUser=(Integer) claims.get("id");

        //操作时间
        LocalDateTime operateTime=LocalDateTime.now();

        //操作类名
        String className=joinPoint.getTarget().getClass().getName();

        //操作方法名
        String methodName=joinPoint.getSignature().getName();

        //方法参数
        Object[] args=joinPoint.getArgs();
        String methodParams= Arrays.toString(args);

        //1、记录开始时间
        long begin = System.currentTimeMillis();
        //2、调用方法
        Object result=joinPoint.proceed();
        //3、记录结束时间
        long end=System.currentTimeMillis();

        //获取返回值
        String returnValue=JSONObject.toJSONString(result);

        //操作时间
        Long costTime=end-begin;
        //传进去参数给属性赋值
        OperateLog operateLog=new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);

        log.info("Aop操作日志:{}",operateLog);
        return  result;
    }
}
