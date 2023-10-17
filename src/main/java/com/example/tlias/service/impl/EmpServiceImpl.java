package com.example.tlias.service.impl;

import com.example.tlias.mapper.EmpMapper;
import com.example.tlias.pojo.Emp;
import com.example.tlias.pojo.PageBean;
import com.example.tlias.pojo.Result;
import com.example.tlias.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;

    /**
     * 这是原始的分页查询操作的Service层
     * @param page
     * @param pageSize
     * @return
     */
//    @Override
//    public PageBean selectPage(Integer page, Integer pageSize) {
//        Long count=empMapper.count();
//        List list=empMapper.selectPage(pageSize*(page-1),pageSize);
//        PageBean pageBean=new PageBean();
//        pageBean.setRows(list);
//        pageBean.setTotal(count);
//        return pageBean;
//    }

    /**
     * 这是使用了pagehelper插件的分页查询操作
     */

    @Override
    public PageBean selectPage(Integer page, Integer pageSize, String name, Short gender, LocalDate begin,LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、执行查询
        List<Emp> empList=empMapper.selectPage(name, gender, begin, end);
        Page<Emp> pHelper=(Page<Emp>) empList;//强转为Page类型

        PageBean pageBean=new PageBean();
        pageBean.setRows(pHelper.getResult());//获取结果列表
        pageBean.setTotal(pHelper.getTotal());//获取记录数
        return pageBean;
    }

    /**
     * 根据ID查询部门员工
     * @param ids
     */
    @Override
    public void deleteByIds(List ids) {
        empMapper.deleteByIds(ids);
    }


    /**
     * 新增部门员工
     * @param emp
     */
    @Override
    public void insertEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insertEmp(emp);
    }

    /**
     * 根据ID查询员工信息
     * @param id
     */
    @Override
    public Emp SelectById(Integer id) {
        return  empMapper.SelectById(id);
    }

    @Override
    public void updateEmp(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateEmp(emp);
    }

    /**
     * 登录确认
     * @param emp
     */
    @Override
    public Emp login(Emp emp) {
        return  empMapper.GetUsernameAndPassword(emp);
    }
}
