package com.example.mysaadmin.module.system.login.domain;

import com.example.mysabase.module.support.captcha.domain.CaptchaForm;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm extends CaptchaForm {

    @NotBlank(message = "登录账号不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Integer loginDevice;

    private String emailCode;

}
