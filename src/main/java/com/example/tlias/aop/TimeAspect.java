package com.example.tlias.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
//@Order(1)//order注解是用来指定不同切面类的通知执行顺序
//@Aspect//Aop类(切面类)
public class TimeAspect {
    //该注解的作用是将公共的切点表达式抽取出来，需要用到时引用该切点表达式即可。
    //定义切入点,写一个方法不需要参数和方法体,只需打上Pointcut注解并写入切入点表达式，那么后面的方法就不需要在一个一个写切入点表达式了，并且改动的时候只需要改动这一处即可
    @Pointcut("execution(* com.example.tlias.service.impl.DeptServiceImpl.*(..))")
    public void pointCut(){}

/**
     @Pointcut("execution(* com.example.tlias.service.impl.EmpServiceImpl.deleteByIds(..)) || " +
             "execution(* com.example.tlias.service.impl.EmpServiceImpl.insertEmp(..))"
            )
     **/
    //注意：根据业务需要，可以使用且(&&)、或()、非（!）来组合比较复杂的切入点表达式。
    @Around("pointCut()")//切入点表达式
    public Object recordTime(ProceedingJoinPoint joinPoint)throws Throwable{
        //1、记录开始时间
            long begin = System.currentTimeMillis();
        //2、调用方法
            Object result=joinPoint.proceed();

        //3、记录结束时间
            long end=System.currentTimeMillis();

            //joinPoint.getSignature()，获取方法的名字
            log.info(joinPoint.getSignature()+"方法耗时：{}ms",begin-end+"Around通知的测试");

            //获取目标方法的返回值
            log.info("目标方法的返回值:{}",result);
            return result;
    }

    //对于@Around通知，获取连接点信息只能使用ProceedingJoinPoint
    //对于其他四种通知，获取连接点信息只能使用JoinPoint ，它是 ProceedingJoinPoint的父类型
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        log.info("before通知的测试");
        //获取目标对象的类名
        String className=joinPoint.getTarget().getClass().getName();
        log.info("这是目标对象的类名:{}",className);

        //获取目标对象的方法名
        String methodName=joinPoint.getSignature().getName();
        log.info("目标方法的方法名:{}",methodName);

        //获取目标的参数
        Object[] argsName=joinPoint.getArgs();
        log.info("目标方法的参数:{}", Arrays.toString(argsName));

    }

    @After("pointCut()")
    public void after(){
        log.info("after通知的测试");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        log.info("AfterReturning通知的测试");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        log.info("AfterThrowing通知的测试");
    }
}

 /**
 * 连接点: JoinPoint，可以被AOP控制的方法（暗含方法执行时的相关信息)
 * 通知: Advice，指哪些重复的逻辑，也就是共性功能（最终体现为一个方法)
 * 切入点:PointCut，匹配连接点的条件，通知仅会在切入点方法执行时被应用
 * 切面:Aspect，描述通知与切入点的对应关系（通知+切入点)
 * 目标对象: Target，通知所应用的对象
 *
 *
 *
 *通知类型:
 * @Around:环绕通知，此注解标注的通知方法在目标前、后都被执行。
 * @Before:前置通知，此注解标注的通知方法在目标方法前被执行。
 * @After:后置通知，此注解标注的通知方法在目标方法后被执行，无论是否有异常都会执行
 * @AfterReturning:返回后通知，此注解标注的通知方法在目标方法后被执行，有异常不会执行
 * @AfterThrowing:异常后通知，此注解标注的通知方法发生异常后执行
 *
 *
 * 注意事项:
 * @Around环绕通知需要自己调用PRpceedingJoinPoint.proceed(）来让原始方法执行，其他通知不需要考虑目标方法执行
 * @Around环绕通知方法的返回值，必须指定为0bject，来接收原始方法的返回值。
  *
  *
  * 不同的切面类通知执行顺序:--默认是按照类名进行比较,也可以用Order注解直接比较
  * 目标方法前的通知方法:字母排名靠前的先执行
  * 目标方法后的通知方法:字母排名靠前的后执行
  *
  * 用@Order(数字)加在切面类上来控制顺序
  * 目标方法前的通知方法:数字小的先执行
  * 目标方法后的通知方法:数字小的后执行
  *
  *
  * 切入点表达式:--execution
  * execution(访问修饰符?  返回值.包名.类名.?方法名(方法参数) throws异常?)
  * 其中带?的表示可以省略的部分
  * 访问修饰符:可省略（比如: public.protected)
  * 包名.类名:可省略--一般不建议省略
  * throws异常:可省略（注意是方法上声明抛出的异常，不是实际抛出的异常)
  *
  * 可以使用通配符描述切入点
  * * ︰单个独立的任意符号，可以通配任意返回值、包名、类名、方法名、任意类型的一个参数，也可以通配包、类、方法名的一部分
  * ..︰多个连续的任意符号，可以通配任意层级的包，或任意类型、任意个数的参数
  * 根据业务需要，可以使用且(&&)、或()、非（!）来组合比较复杂的切入点表达式。
  *
  *
  * 在Spring中用JoinPoint抽象了连接点，用它可以获得方法执行时的相关信息，如目标类名、方法名、方法参数等。
  * 对于@Around通知，获取连接点信息只能使用ProceedingJoinPoint
  * 对于其他四种通知，获取连接点信息只能使用JoinPoint ，它是 ProceedingJoinPoint的父类型
 */






