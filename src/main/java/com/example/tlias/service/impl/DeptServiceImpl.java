package com.example.tlias.service.impl;

import com.example.tlias.mapper.DeptLogMapper;
import com.example.tlias.mapper.DeptMapper;
import com.example.tlias.mapper.EmpMapper;
import com.example.tlias.mapper.OperateLogMapper;
import com.example.tlias.pojo.Dept;
import com.example.tlias.pojo.DeptLog;
import com.example.tlias.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private DeptLogServiceImpl deptLogService;
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    /**注解在类上就代表当前类里面的所有方法都交给Spring事物管理,注解在接口上就代表改接口下面的所有类都交给spring事物管理
     * 注解:@Transactional
     * 位置:业务( service）层的方法上、类上、接口上
     * 作用:将当前方法交给spring进行事务管理，方法执行前，开启事务;成功执行完毕，提交事务;出现异常，回滚事务
     * 相关属性(参数)：rollbackFor：配置何种异常出现回滚事物         propagation：配置事物的传播行为
     *propagation使用情况-----当方法a调用方法b的时候，a,b方法都有 @Transactional注解加入了事务管理，在b的@Transactional注解上面配置相关参数
     *propagation配置的参数：REQUIRED--【默认值】需要事务，有则加入，无则创建新事务
     * SUPPORTS--支持事务，有则加入，无则在无事务状态中运行         REQUIRES_NEW--需要新事务，无论有无，总是创建新事务
     * NOT_SUPPORTED--不支持事务，在无事务状态下运行,如果当前存在已有事务,则挂起当前事务
     * MANDATORY--必须有事物，否则抛出异常         NEVER--必须没有事物，否则抛出异常
     */
    @Transactional(rollbackFor = Exception.class)//这个方法当前已经交给Spring事物管理
    @Override
    public void delete(Integer id) throws Exception {
        try {
            deptMapper.deleteById(id);//根据部门ID删除部门
            /**
             * 下面new Exception是异常模拟,默认情况下，只有出现 RuntimeException类型的异常@Transactional才回滚。但是这里的异常不属于RuntimeException
             * 即使出现异常也不会回滚，最终导致数据的不一致
             * 所以就要配置@Transactional里面的参数rollbackFor，rollbackFor属性用于控制出现何种异常，回滚事务。这里Exception.class是表示所有异常都回滚
             */
//            int i=1/0;
//            if (true) {
//                throw new Exception("");
//            }
            empMapper.deleteByDeptID(id);//根据部门ID删除该部门下面的所有员工
        }finally {
            DeptLog deptLog=new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("此次解散部门的ID是:"+id);
            deptLogService.insertLog(deptLog);
            /**
             * 这里的insertLog方法将参数propagation设置为:REQUIRES_NEW(REQUIRES_NEW--需要新事务，无论有无，总是创建新事务)
             * 所以即使delete方法有异常被回滚也影响不到insertLog方法，因为不是一个事物里面的。
             * 但是如果是默认参数REQUIRES的话，那么就代表两个方法在一个事物里面，那么finally里面的代码即使被执行了也会回滚
             */
        }
    }

    @Override
    public void deptInsert(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insertDept(dept);
    }

    @Override
    public Dept selectById(String id) {
        Dept dept=deptMapper.selectById(id);
//      int i=1/0;
        return dept;
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateDept(dept);
    }


}
