package com.example.mysaadmin.module.system.employee.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mysaadmin.module.system.employee.domain.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {

    /**
     * 通过登录名查询
     */
    EmployeeEntity getByLoginName(@Param("loginName") String loginName,@Param("deletedFlag") Boolean deletedFlag);
}
