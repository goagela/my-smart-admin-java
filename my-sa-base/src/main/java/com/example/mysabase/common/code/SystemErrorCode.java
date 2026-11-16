package com.example.mysabase.common.code;

import lombok.Getter;

@Getter
public enum SystemErrorCode implements ErrorCode {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(10001,"系统似乎出了点小问题")
    ;

    private final int code;

    private final String msg;

    private final String level;

    SystemErrorCode(int code,String msg){
        this.code=code;
        this.msg=msg;
        this.level=LEVEL_SYSTEM;
    }

}
