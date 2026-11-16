package com.example.mysaadmin.module.system.department.service;

import com.example.mysaadmin.module.system.department.dao.DepartmentDao;
import com.example.mysaadmin.module.system.department.domain.vo.DepartmentVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Resource
    private DepartmentDao departmentDao;


    //获取部门
    public DepartmentVO getDepartmentById(Long departmentId){
        return departmentDao.selectDepartmentVO(departmentId);
    }

}
