package com.example.mysaadmin.module.system.login.manager;


import com.example.mysaadmin.module.system.department.domain.vo.DepartmentVO;
import com.example.mysaadmin.module.system.department.service.DepartmentService;
import com.example.mysaadmin.module.system.employee.dao.EmployeeDao;
import com.example.mysaadmin.module.system.employee.domain.entity.EmployeeEntity;
import com.example.mysaadmin.module.system.employee.service.EmployeeService;
import com.example.mysaadmin.module.system.login.domain.RequestEmployee;
import com.example.mysabase.common.enumeration.UserTypeEnum;
import com.example.mysabase.common.util.SmartBeanUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *  *LoginService 负责完整的登录业务流程，例如：
 *   接收登录参数
 *   → 校验账号密码
 *   → 生成 Sa-Token
 *   → 组装登录结果
 *   → 保存登录日志
 *   → 处理登录失败次数
 *  LoginManager 负责登录用户信息和权限的具体管理，例如：
 *   查询员工信息
 *   组装 RequestEmployee
 *   查询部门名称
 *   转换头像 URL
 *   缓存用户信息
 *   缓存用户权限
 *   清理用户缓存
 */

@Service
public class LoginManager {

    @Resource
    private DepartmentService departmentService;

    @Resource
    private  EmployeeService employeeService;

    /**
     * 根据用户id获取用户实体，再调用loadLoginInfo返回用户完整信息
     * @param requestEmployeeId
     * @return
     */
    public RequestEmployee getRequestEmployee(Long requestEmployeeId){

        EmployeeEntity employeeEntity= employeeService.getById(requestEmployeeId);
        if (employeeEntity == null) {
            return null;
        }

        return loadLoginInfo(employeeEntity);

    }

    /**
     * 根据用户实体返回用户完整信息，因为用户完整信息还包含部门等信息不只是查询用户表就能完成
     * @return
     */
    public RequestEmployee loadLoginInfo(EmployeeEntity employeeEntity){

        //设置基础信息
        RequestEmployee requestEmployee= SmartBeanUtil.copy(employeeEntity,RequestEmployee.class);
        requestEmployee.setUserType(UserTypeEnum.ADMIN_EMPLOYEE);

        //设置部门信息
        DepartmentVO department=departmentService.getDepartmentById(employeeEntity.getDepartmentId());
        requestEmployee.setDepartmentName(department!=null?department.getDepartmentName():"");

        //设置头像信息,todo
        String avatar=employeeEntity.getAvatar();

        return requestEmployee;
    }

}
