package com.example.tlias.mapper;

import com.example.tlias.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> list();

    @Delete("delete  from dept where id=#{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void insertDept(Dept dept);

    @Select("select * from  dept where  id=#{id}")
    Dept selectById(String id);

    @Update("update dept set name =#{name} where id=#{id}")
    void updateDept(Dept dept);
}
