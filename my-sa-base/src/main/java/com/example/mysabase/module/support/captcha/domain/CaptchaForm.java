package com.example.mysabase.module.support.captcha.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CaptchaForm {

    @NotBlank(message = "验证码不能为空")
    private String captchaCode;

    @NotBlank(message = "验证码uuid标识不能为空")
    private String captchaUuid;
}
