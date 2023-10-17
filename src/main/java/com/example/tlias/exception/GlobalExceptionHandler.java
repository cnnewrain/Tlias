package com.example.tlias.exception;

import com.example.tlias.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器,捕获整个项目出现的异常
 */
@RestControllerAdvice//包含了@ResponseBody所以返回值自动变成JSON格式
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//这个注解是捕获异常,参数是捕获所有异常
    public Result ex(Exception ex){
        ex.printStackTrace();//输出异常的堆栈信息
        return Result.error("出现异常");
    }
}
