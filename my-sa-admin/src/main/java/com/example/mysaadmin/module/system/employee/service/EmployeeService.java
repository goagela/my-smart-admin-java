package com.example.mysaadmin.module.system.employee.service;

import com.example.mysaadmin.module.system.employee.dao.EmployeeDao;
import com.example.mysaadmin.module.system.employee.domain.entity.EmployeeEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Resource
    private EmployeeDao employeeDao;


    /**
     * 根据登录名获取员工
     * 传入 false 表示只查询未删除用户
     */
    public EmployeeEntity getByLoginName(String loginName){
        return employeeDao.getByLoginName(loginName,false);
    }
}
