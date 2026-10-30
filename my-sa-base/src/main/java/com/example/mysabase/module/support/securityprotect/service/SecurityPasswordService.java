package com.example.mysabase.module.support.securityprotect.service;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityPasswordService {

    static Argon2PasswordEncoder ARGON2_PASSWORD_ENCODER = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    /**
     * 获取 加密后 的密码
     */
    public static String getEncryptPwd(String password) {
        return ARGON2_PASSWORD_ENCODER.encode(password);
    }

    /**
     * 校验密码是否匹配
     */
    public static Boolean matchesPwd(String password, String encodedPassword) {
        return ARGON2_PASSWORD_ENCODER.matches(password, encodedPassword);
    }
}
