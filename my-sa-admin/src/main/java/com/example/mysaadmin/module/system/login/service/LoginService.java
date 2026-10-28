package com.example.mysaadmin.module.system.login.service;

import com.example.mysaadmin.module.system.login.domain.LoginResultVO;
import com.example.mysabase.common.domian.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {


    public ResponseDTO<LoginResultVO> login(){
        return ResponseDTO.ok();
    }
}
