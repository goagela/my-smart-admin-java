package com.example.mysaadmin.module.system.login.controller;

import com.example.mysaadmin.module.system.login.domain.LoginForm;
import com.example.mysaadmin.module.system.login.domain.LoginResultVO;
import com.example.mysaadmin.module.system.login.service.LoginService;
import com.example.mysabase.common.domian.ResponseDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController{

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseDTO<LoginResultVO> login(@Valid @RequestBody LoginForm loginForm,HttpServletRequest loginRequest) {
        return loginService.login();
    }
}



