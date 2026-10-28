package com.example.mysabase.module.support.captcha.domain;


import lombok.Data;

/**
 * 图形验证码VO
 */
@Data
public class CaptchaVO {

    //验证码uuid标识
    private String captchaUuid;

    //验证码图片内容
    private String captchaText;

    //验证码base64图片
    private String captchaBase64Image;

    //验证码过期时间
    private Long expireSeconds;
}
