package com.example.mysabase.common.domian;

import com.example.mysabase.common.enumeration.SystemEnvironmentEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class SystemEnvironment {
    /**
     * 是否为生产环境
     */
    private boolean isProd;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 当前环境
     */
    private SystemEnvironmentEnum currentEnvironment;
}
