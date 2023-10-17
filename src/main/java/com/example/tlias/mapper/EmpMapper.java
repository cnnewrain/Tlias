package com.example.tlias.mapper;

import com.example.tlias.pojo.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EmpMapper {

    /**
     * 这是原始的分页查询操作
     * @return
     */
//    @Select("select count(*) from emp")
//    public Long count();
//    @Select("select * from emp limit #{start},#{pageSize}")
//    public List<Emp> selectPage(Integer start,Integer pageSize);

    /**
     * 这是使用了pagehelper插件后的分页查询操作
     */
    //@Select("select * from emp")，这里用xml文件映射了
    public  List<Emp> selectPage(String name, Short gender, LocalDate begin, LocalDate end);

    /**
     * 删除员工操作
     * @param ids
     */
    void deleteByIds(List ids);

   @Insert("insert into emp(username,name ,gender,image,job,entrydate,dept_id,create_time,update_time)values (#{username}," +
           "#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    void insertEmp(Emp emp);

   @Select("select * from emp where id=#{id}")
    Emp SelectById(Integer id);

    void updateEmp(Emp emp);

    @Select("select * from emp where username =#{username} && password=#{password}")
    Emp GetUsernameAndPassword(Emp emp);

    /**
     * 根据部门ID删除改部门下面的所有员工
     */
    @Delete("delete from emp where dept_id=#{deptId}")
    void deleteByDeptID(Integer deptId);
}
