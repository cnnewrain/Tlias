package com.example.tlias.Controller;

import com.example.tlias.annotation.MyAopLog;
import com.example.tlias.pojo.Dept;
import com.example.tlias.pojo.Result;
import com.example.tlias.service.DeptService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理Controller
 */
@Slf4j//加入这个注解后，可以不用创建对象而直接调用info方法
@RestController
//@RequestMapping("/depts") 由于这是部门管理页面的操作，所以路劲都要用depts，所以可以把depts这个注解路径写在类上面这样下面的路径就可以删掉depts路径了,这里我选择我没用这个方法
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门的全部信息
     * @return
     */
   // private static Logger logger= LoggerFactory.getLogger(DeptController.class);//日志记录
    @RequestMapping(value = "/depts",method = RequestMethod.GET)//这个method参数是指定请求方式类型,这里指定请求方式为get
    //@GetMapping("/depts")//也可以直接用GetMapping注解直接限定请求为GET,并且其已经包含RequestMapping注解，更为简单
    public Result list(){
        log.info("查询全部部门数据");//输出信息.没有使用System输出
        List<Dept> deptList=deptService.list();
        return  Result.success(deptList);
    }

    /**
     * 删除部门
     * @return
     */
    @MyAopLog
    @RequestMapping(value = "/depts/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable Integer id) throws Exception{
        log.info("根据ID删除部门:{}",id);
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     */
    @MyAopLog
    @RequestMapping(value = "/depts",method = RequestMethod.POST)
    public Result insert(@RequestBody Dept dept){
        log.info("新增部门:{}",dept);
        deptService.deptInsert(dept);
        return  Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @MyAopLog
    @RequestMapping(value = "/depts/{id}",method = RequestMethod.GET)
    public Result selectById(@PathVariable String id){
        log.info("这是根据ID查询数据:{}",id);
        Dept dept=deptService.selectById(id);
        return Result.success(dept);
    }

    /**
     * 更新部门
     * @param dept
     * @return
     */
    @MyAopLog
    @RequestMapping(value = "/depts",method = RequestMethod.PUT)
    public Result updateDept(@RequestBody Dept dept){
        log.info("更新数据:",dept);
        deptService.updateDept(dept);
        return Result.success();
    }
}
