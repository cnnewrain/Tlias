package com.example.tlias.service.impl;

import com.example.tlias.mapper.DeptLogMapper;
import com.example.tlias.pojo.DeptLog;
import com.example.tlias.service.DeptLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DeptLogServiceImpl implements DeptLogService {

    @Autowired
    private DeptLogMapper deptLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertLog(DeptLog deptLog) {
        deptLogMapper.InsertLog(deptLog);
    }
}
