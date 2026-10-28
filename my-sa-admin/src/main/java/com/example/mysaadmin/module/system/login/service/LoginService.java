package com.example.mysaadmin.module.system.login.service;

import com.example.mysaadmin.module.system.login.domain.LoginResultVO;
import com.example.mysabase.common.domian.ResponseDTO;
import com.example.mysabase.module.support.captcha.domain.CaptchaVO;
import com.example.mysabase.module.support.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Resource
    private CaptchaService captchaService;

    public ResponseDTO<LoginResultVO> login(){
        return ResponseDTO.ok();
    }

    /**
     * 获取验证码
     */
    public ResponseDTO<CaptchaVO> getCaptcha() {
        return ResponseDTO.ok(captchaService.generateCaptcha());
    }
}
