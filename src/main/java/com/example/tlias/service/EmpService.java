package com.example.tlias.service;

import com.example.tlias.pojo.Emp;
import com.example.tlias.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {
    PageBean selectPage(Integer page, Integer pageSize, String name, Short gender, LocalDate begin,LocalDate end);

    void deleteByIds(List ids);

    void insertEmp(Emp emp);

    Emp SelectById(Integer id);

    void updateEmp(Emp emp);

    Emp login(Emp emp);
}
