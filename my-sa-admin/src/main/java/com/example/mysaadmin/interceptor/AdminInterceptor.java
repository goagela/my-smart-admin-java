package com.example.mysaadmin.interceptor;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaAnnotationStrategy;
import com.example.mysaadmin.module.system.login.domain.RequestEmployee;
import com.example.mysaadmin.module.system.login.service.LoginService;
import com.example.mysabase.common.annotation.NoNeedLogin;
import com.example.mysabase.common.code.SystemErrorCode;
import com.example.mysabase.common.code.UserErrorCode;
import com.example.mysabase.common.domian.ResponseDTO;
import com.example.mysabase.common.util.SmartRequestUtil;
import com.example.mysabase.common.util.SmartResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    private final LoginService loginService;

    public AdminInterceptor(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){

        //OPTIONS方法不放行，直接return，并设置状态码204
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        //静态资源请求（如请求图片，css，html）直接放行
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        try {
            //---------------1.获取token，查询LoginId并获取用户----------------------
            String token= StpUtil.getTokenValue();
            String loginId= (String) StpUtil.getLoginIdByToken(token);
            RequestEmployee requestEmployee=loginService.getLoginEmployee(loginId,request);

            //-----------------------2.校验并登录----------------------------------
            Method method = ((HandlerMethod) handler).getMethod();
            NoNeedLogin noNeedLogin = ((HandlerMethod) handler).getMethodAnnotation(NoNeedLogin.class);
            if (noNeedLogin != null) {
                if (requestEmployee==null){
                    return true;
                }else {
                    SmartRequestUtil.setRequestUser(requestEmployee);
                    StpUtil.updateLastActiveToNow();
                    return true;
                }
            }

            //访问需要登录的接口时
            if (requestEmployee==null){
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.LOGIN_STATE_INVALID));
                return false;
            }
            //续签token
            StpUtil.updateLastActiveToNow();

            //-----------------------------3.校验权限----------------------------------
            SmartRequestUtil.setRequestUser(requestEmployee);

            if (SaAnnotationStrategy.instance.isAnnotationPresent.apply(method, SaIgnore.class)) {
                return true;
            }

            // 如果是超级管理员的话，不需要校验权限
            if (requestEmployee.getAdministratorFlag()) {
                return true;
            }

            SaAnnotationStrategy.instance.checkMethodAnnotation.accept(method);

        }catch (SaTokenException e){

            /*
             * sa-token 异常状态码
             * 具体请看： https://sa-token.cc/doc.html#/fun/exception-code
             */
            int code = e.getCode();
            if (code == 11041 || code == 11051) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.NO_PERMISSION));
            } else if (code == 11016) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.LOGIN_ACTIVE_TIMEOUT));
            } else if (code >= 11011 && code <= 11015) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.LOGIN_STATE_INVALID));
            } else {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.PARAM_ERROR));
            }
            return false;
        }catch (Throwable e) {
            SmartResponseUtil.write(
                    response,
                    ResponseDTO.error(SystemErrorCode.SYSTEM_ERROR)
            );
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 清除上下文
        SmartRequestUtil.remove();
    }



}
