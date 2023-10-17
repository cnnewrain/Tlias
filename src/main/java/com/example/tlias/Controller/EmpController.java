package com.example.tlias.Controller;

import com.example.tlias.annotation.MyAopLog;
import com.example.tlias.pojo.Emp;
import com.example.tlias.pojo.PageBean;
import com.example.tlias.pojo.Result;
import com.example.tlias.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
public class EmpController {
    @Autowired
    EmpService empService;
    /**
     * @request 前端请求参数:页码,每页展示记录数
     * @param page, 如果前端没有传进参数,那么默认就是1
     * @param pageSize, 如果前端没有传进参数,那么默认就是10
     * @return 返回一个封装了总记录数和结果列表的类对象(pageBean)
     */
    @RequestMapping(value = "/emps",method = RequestMethod.GET)
    public Result selectPage(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String name, Short gender,
                             @DateTimeFormat(pattern = "yyyy--MM--dd") LocalDate begin,
                             @DateTimeFormat(pattern = "yyyy--MM--dd") LocalDate end){
        log.info("分页查询:{},{},{},{},{},{}",page,pageSize,name,gender,begin,end);
        PageBean pageBean=empService.selectPage(page,pageSize, name, gender, begin, end);
        return Result.success(pageBean);
    }


    /**
     * 删除部门员工
     * @param ids
     * @return
     */
    @MyAopLog
    @RequestMapping(value = "/emps/{ids}",method = RequestMethod.DELETE)
    public Result deleteByIds(@PathVariable List ids){
        log.info("批量删除操作:{}",ids);
        empService.deleteByIds(ids);
        return Result.success();
    }


    /**
     * 新增部门员工
     * @param emp
     * @return
     */
    @MyAopLog
    @RequestMapping(value = "/emps",method = RequestMethod.POST)
    public Result insertEmp(@RequestBody Emp emp){
        log.info("新增员工信息:{}",emp);
        empService.insertEmp(emp);
        return  Result.success();
    }


    /**
     * 根据ID查询员工信息
     * @param id
     * @return
     */
    @MyAopLog
    @RequestMapping(value = "/emps/{id}",method = RequestMethod.GET)
    public Result SelectById(@PathVariable Integer id){
        log.info("根据ID查询员工信息,ID:{}",id);
        Emp emp=empService.SelectById(id);
        return Result.success(emp);
    }

    @MyAopLog
    @RequestMapping(value = "/emps",method = RequestMethod.PUT)
    public Result updateEmp(@RequestBody Emp emp){
        log.info("修改员工信息:{}",emp);
        empService.updateEmp(emp);
        return Result.success();
    }

}
