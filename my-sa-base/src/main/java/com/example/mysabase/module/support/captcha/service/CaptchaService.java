package com.example.mysabase.module.support.captcha.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.mysabase.common.domian.ResponseDTO;
import com.example.mysabase.common.domian.SystemEnvironment;
import com.example.mysabase.constant.RedisKeyConst;
import com.example.mysabase.module.support.captcha.domain.CaptchaForm;
import com.example.mysabase.module.support.captcha.domain.CaptchaVO;
import com.example.mysabase.module.support.redis.RedisService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Objects;

@Service
public class CaptchaService {

    private static final long EXPIRE_SECOND=60L;

    @Resource
    private SystemEnvironment systemEnvironment;

    @Resource
    private RedisService redisService;

    /**
     * 生成图形验证码，60s过期
     */
    public CaptchaVO generateCaptcha(){

        //生成四位数验证码
        String captchaText= RandomUtil.randomNumbers(4);

        //定义图形验证码的长、宽、验证码位数、干扰线数量
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(125, 43, 4, 80);

        //设置背景颜色
        lineCaptcha.setBackground(new Color(230, 244, 255));

        //生成图片
        Image image = lineCaptcha.createImage(captchaText);

        //转为base64
        String base64Code = ImgUtil.toBase64(image, "jpg");

        /*
         * 返回验证码对象
         * 图片 base64格式
         */
        // uuid 唯一标识
        String uuid = IdUtil.fastSimpleUUID();

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaUuid(uuid);
        captchaVO.setCaptchaBase64Image("data:image/png;base64," + base64Code);
        captchaVO.setExpireSeconds(EXPIRE_SECOND);
        if (!systemEnvironment.isProd()) {
            captchaVO.setCaptchaText(captchaText);
        }
        String redisCaptchaKey=redisService.generateRedisKey(RedisKeyConst.CAPTCHA,uuid);
        redisService.set(redisCaptchaKey,captchaText,EXPIRE_SECOND);

        return captchaVO;
    }

    /**
     * 校验图形验证码
     */
    public ResponseDTO<Void> checkCaptcha(CaptchaForm captchaForm){
        if (StringUtils.isBlank(captchaForm.getCaptchaUuid()) || StringUtils.isBlank(captchaForm.getCaptchaCode())) {
            return ResponseDTO.userErrorParam("请输入正确验证码");
        }

        String redisCaptchaKey = redisService.generateRedisKey(RedisKeyConst.CAPTCHA, captchaForm.getCaptchaUuid());
        String redisCaptchaCode = redisService.get(redisCaptchaKey);
        if (StringUtils.isBlank(redisCaptchaCode)) {
            return ResponseDTO.userErrorParam("验证码已过期，请刷新重试");
        }
        if (!Objects.equals(redisCaptchaCode, captchaForm.getCaptchaCode())) {
            return ResponseDTO.userErrorParam("验证码错误，请输入正确的验证码");
        }
        // 校验成功，删除已使用的验证码
        redisService.delete(redisCaptchaKey);
        return ResponseDTO.ok();
    }

}
