package com.example.tlias.Controller;


import com.example.tlias.pojo.Emp;
import com.example.tlias.pojo.Result;
import com.example.tlias.service.EmpService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Emp emp){
        log.info("输入的账号密码是:{}",emp);
        Emp JudgeEmp=empService.login(emp);
        //如果登录成功就要生成JWT令牌并下发
        Map<String,Object> map = new HashMap();
        if (JudgeEmp != null){
            map.put("id",JudgeEmp.getId());
            map.put("name",JudgeEmp.getName());
            map.put("username",JudgeEmp.getUsername());
            map.put("password",JudgeEmp.getPassword());
            String jwt=JwtUtils.generateJwt(map);//JWT令牌包含了当前返回的员工信息
            log.info("JWT令牌：{}",jwt);
            return Result.success(jwt);
        }else{
            return Result.success("用户名或密码错误");
        }
    }
}
