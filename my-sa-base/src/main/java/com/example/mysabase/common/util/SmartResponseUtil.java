package com.example.mysabase.common.util;



import com.alibaba.fastjson.JSON;
import com.example.mysabase.common.domian.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SmartResponseUtil {
    public static void write(HttpServletResponse response, ResponseDTO<?> responseDTO){
        response.setContentType("application/json;charset=UTF-8");

        try {
            response.getWriter().write(JSON.toJSONString(responseDTO));
        }catch (IOException e){
            log.info(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
