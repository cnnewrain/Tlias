package com.example.tlias.service;

import com.example.tlias.pojo.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {
    List<Dept> list();

    void delete(Integer id) throws Exception;

    void deptInsert(Dept dept);


    Dept selectById(String id);

    void updateDept(Dept dept);
}
