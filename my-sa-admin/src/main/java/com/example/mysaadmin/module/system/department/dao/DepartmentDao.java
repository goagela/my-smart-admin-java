package com.example.mysaadmin.module.system.department.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mysaadmin.module.system.department.domain.entity.DepartmentEntity;
import com.example.mysaadmin.module.system.department.domain.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepartmentDao extends BaseMapper<DepartmentEntity> {


    DepartmentVO selectDepartmentVO(@Param("departmentId") Long departmentId);
}
