package com.example.mysaadmin.module.system.department.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

public class DepartmentEntity {

    /**
     * 主键id
     */
    @TableId(type= IdType.AUTO)
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 负责人员工 id
     */
    private Long managerId;

    /**
     * 部门父级id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    //更新时间
    private LocalDateTime updateTime;

    //创建时间
    private LocalDateTime createTime;

}
