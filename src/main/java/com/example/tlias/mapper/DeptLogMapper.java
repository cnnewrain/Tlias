package com.example.tlias.mapper;
import com.example.tlias.pojo.DeptLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DeptLogMapper {

    @Insert("insert into dept_log(create_time,description) values (#{CreateTime},#{Description})")
    void InsertLog(DeptLog deptLog);
}
