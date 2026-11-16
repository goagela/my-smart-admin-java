package com.example.mysaadmin.module.system.login.service;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.example.mysaadmin.module.system.login.domain.LoginResultVO;
import com.example.mysaadmin.module.system.login.domain.RequestEmployee;
import com.example.mysaadmin.module.system.login.manager.LoginManager;
import com.example.mysabase.common.domian.ResponseDTO;
import com.example.mysabase.module.support.captcha.domain.CaptchaVO;
import com.example.mysabase.module.support.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

    /**
     * 万能密码的 sa token loginId 前缀
     */
    private static final String SUPER_PASSWORD_LOGIN_ID_PREFIX = "S";

    @Resource
    private CaptchaService captchaService;
    @Autowired
    private LoginManager loginManager;

    public ResponseDTO<LoginResultVO> login(){
        return ResponseDTO.ok();
    }

    /**
     * 获取验证码
     */
    public ResponseDTO<CaptchaVO> getCaptcha() {
        return ResponseDTO.ok(captchaService.generateCaptcha());
    }

    /**
     * 根据 loginId 获取 员工id
     */
    private Long getEmployeeIdByLoginId(String loginId){
        if(loginId==null){
            return null;
        }
        try {
            String employeeIdStr=null;
            //普通loginId格式-  1:员工ID  ；    万能密码loginId格式-  S:UUID:员工ID
            if(loginId.startsWith(SUPER_PASSWORD_LOGIN_ID_PREFIX)){
                employeeIdStr=loginId.split(":")[2];
            }else{
                employeeIdStr=loginId.substring(2);
            }
            return Long.parseLong(employeeIdStr);
        }catch (Exception e){
            log.error("loginId parse error , loginId : {}", loginId, e);
            return null;
        }
    }

    /**
     * 调用getEmployeeIdByLoginId根据loginId获取员工id，再用员工id获取请求员工信息
     * @param loginId
     * @param request
     * @return
     */
    public RequestEmployee getLoginEmployee(String loginId, HttpServletRequest request){
        if (loginId == null) {
            return null;
        }

        Long requestEmployeeId=getEmployeeIdByLoginId(loginId);
        if (requestEmployeeId == null) {
            return null;
        }
        RequestEmployee requestEmployee=loginManager.getRequestEmployee(requestEmployeeId);
        if (requestEmployee == null) {
            return null;
        }

        // 更新请求ip和user agent
        requestEmployee.setUserAgent(JakartaServletUtil.getHeaderIgnoreCase(request, "user-agent"));
        requestEmployee.setIp(JakartaServletUtil.getClientIP(request));

        return requestEmployee;
    }

}
